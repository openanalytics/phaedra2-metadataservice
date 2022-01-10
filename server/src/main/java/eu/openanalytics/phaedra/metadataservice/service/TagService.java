package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    public void addObjectTag(TaggedObjectDTO taggedObjectDTO) {
        Tag tag = tagRepository.findByName(taggedObjectDTO.getTag());
        if (tag == null) {
            tag = tagRepository.save(new Tag(taggedObjectDTO.getTag()));
        }
        TaggedObject taggedObject = new TaggedObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass(), tag.getId());
        tag.addObject(taggedObject);
        tagRepository.save(tag);
    }

    public void removeObjectTag(TaggedObjectDTO taggedObjectDTO) {
        Tag tag = tagRepository.findByName(taggedObjectDTO.getTag());
        tag.removeObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass());
        tagRepository.save(tag);
    }

    public List<TagDTO> getAllTags() {
        List<Tag> result = (List<Tag>) tagRepository.findAll();
        return result.stream().map(modelMapper::map).collect(Collectors.toList());
    }

    public List<TagDTO> getTagsByObjectId(Long objectId) {
        List<Tag> result = tagRepository.findByObjectId(objectId);
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

}
