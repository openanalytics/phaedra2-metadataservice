/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.metadataservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.repository.TagRepository;
import eu.openanalytics.phaedra.metadataservice.repository.TaggedObjectRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TaggedObjectRepository taggedObjectRepository;

    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public TagService(TagRepository tagRepository, TaggedObjectRepository taggedObjectRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.taggedObjectRepository = taggedObjectRepository;
        this.modelMapper = modelMapper;
    }

    public void addObjectTag(TaggedObjectDTO taggedObjectDTO) {
        Tag tag = tagRepository.findByName(taggedObjectDTO.getTag());
        if (tag == null) {
            tag = tagRepository.save(new Tag(taggedObjectDTO.getTag()));
        }
        TaggedObject taggedObject = new TaggedObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass(), tag.getId());
        taggedObjectRepository.save(taggedObject);
    }

    public void removeObjectTag(TaggedObjectDTO taggedObjectDTO) {
    	Tag tag = tagRepository.findByName(taggedObjectDTO.getTag());
    	if (tag != null) {
    		taggedObjectRepository.deleteByTagIdAndObjectIdAndObjectClass(
    				tag.getId(), taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass());
    	}
    }

    public List<TagDTO> getAllTags() {
        List<Tag> result = (List<Tag>) tagRepository.findAll();
        return result.stream().map(modelMapper::map).collect(Collectors.toList());
    }

    public List<TagDTO> getTagsByObjectClass(String objectClass) {
        List<Tag> result = tagRepository.findByObjectClass(objectClass);
        return result.stream().map(modelMapper::map).collect(Collectors.toList());
    }

    public List<TagDTO> getTagsByObjectIdAndObjectClass(Long objectId, String objectClass) {
        List<Tag> result = tagRepository.findByObjectIdAndObjectClass(objectId, objectClass);
        return result.stream().map(modelMapper::map).collect(Collectors.toList());
    }

    public Map<Long, List<TagDTO>> getTagsByObjectIdsAndObjectClass(Set<Long> objectIds, String objectClass) {
    	List<TaggedObject> taggedObjects = taggedObjectRepository.findByObjectIdInAndObjectClass(objectIds, objectClass);

    	Set<Long> tagIds = taggedObjects.stream().map(TaggedObject::getTagId).collect(Collectors.toSet());
    	List<Tag> tags = tagRepository.findByIdIn(tagIds);

    	logger.info(String.format("Found %d tagged objects: %s", taggedObjects.size(), taggedObjects));
    	logger.info(String.format("Found %d tags: %s", tags.size(), tags));
    	
    	// For each object ID, map its TaggedObjects to Tags.
    	Function<TaggedObject, TagDTO> tagFinder = to -> tags.stream()
    			.filter(t -> t.getId() == to.getTagId())
    			.findAny()
    			.map(modelMapper::map)
    			.orElse(null);

    	Map<Long, List<TagDTO>> mappedTags = new HashMap<>();
    	for (Long objectId: objectIds) {
    		List<TagDTO> objectTags = taggedObjects.stream().filter(to -> to.getObjectId() == objectId).map(tagFinder).toList();
    		logger.info(String.format("Tags for object %d: %s", objectId, objectTags));
    		mappedTags.put(objectId, objectTags);
    	}
    	return mappedTags;
//    	return taggedObjects.stream().collect(
//    			Collectors.groupingBy(TaggedObject::getObjectId, Collectors.mapping(tagFinder, Collectors.toList())));
    }
}
