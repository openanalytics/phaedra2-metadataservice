/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.repository.TagRepository;
import eu.openanalytics.phaedra.metadataservice.repository.TaggedObjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TaggedObjectRepository taggedObjectRepository;

    private final ModelMapper modelMapper;

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
            TaggedObject taggedObject = taggedObjectRepository.findByObjectIdAndObjectClassAndTagId(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass(), tag.getId());
            if (taggedObject != null) {
                taggedObjectRepository.delete(taggedObject);
            }
        }
    }

    public List<TagDTO> getAllTags() {
        List<Tag> result = (List<Tag>) tagRepository.findAll();
        return result.stream().map(modelMapper::map).toList();
    }

    public List<TagDTO> getTagsByObjectClass(ObjectClass objectClass) {
        List<Tag> result = tagRepository.findByObjectClass(objectClass);
        return result.stream().map(modelMapper::map).toList();
    }

    public List<TagDTO> getTagsByObjectIdAndObjectClass(Long objectId, ObjectClass objectClass) {
        List<Tag> result = tagRepository.findByObjectIdAndObjectClass(objectId, objectClass);
        return result.stream().map(modelMapper::map).toList();
    }

    public Map<Long, List<TagDTO>> getTagsByObjectIdsAndObjectClass(Set<Long> objectIds, ObjectClass objectClass) {
    	List<TaggedObject> taggedObjects = taggedObjectRepository.findByObjectIdInAndObjectClass(objectIds, objectClass);

    	Set<Long> tagIds = taggedObjects.stream().map(TaggedObject::getTagId).collect(Collectors.toSet());
    	List<Tag> tags = tagRepository.findByIdIn(tagIds);
    	Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, t -> t));

    	Map<Long, List<TagDTO>> mappedTags = new HashMap<>();
    	for (Long objectId: objectIds) {
    		List<TagDTO> objectTags = taggedObjects.stream()
    				.filter(to -> to.getObjectId().equals(objectId))
    				.map(to -> tagMap.get(to.getTagId()))
    				.map(modelMapper::map).toList();
    		mappedTags.put(objectId, objectTags);
    	}
    	return mappedTags;
    }
}
