package eu.openanalytics.phaedra.metadataservice.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    	
    	Set<Long> tagIds = taggedObjects.stream().map(to -> to.getTagId()).distinct().collect(Collectors.toSet());
    	List<Tag> tags = tagRepository.findByIdIn(tagIds);
    	
    	// For each object ID, map its TaggedObjects to Tags.
    	Function<TaggedObject, TagDTO> tagFinder = to -> tags.stream()
    			.filter(t -> t.getId() == to.getTagId())
    			.findAny()
    			.map(modelMapper::map)
    			.orElse(null);
    	
    	return taggedObjects.stream().collect(
    			Collectors.groupingBy(TaggedObject::getObjectId, Collectors.mapping(tagFinder, Collectors.toList())));
    }
}
