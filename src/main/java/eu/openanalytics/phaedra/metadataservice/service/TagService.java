package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public void addObjectTag(TaggedObjectDTO taggedObjectDTO) {
        Tag tag = tagRepository.findByName(taggedObjectDTO.getTag());
        if (tag == null) {
            tag = tagRepository.save(new Tag(taggedObjectDTO.getTag()));
        }
        TaggedObject taggedObject = new TaggedObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass().name(), tag.getId());
        tag.addObject(taggedObject);
        tagRepository.save(tag);
    }

    public void removeObjectTag(TaggedObjectDTO taggedObjectDTO) {
        Tag tag = tagRepository.findByName(taggedObjectDTO.getTag());
        tag.removeObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass().name());
        tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    public List<Tag> getTagsByObjectId(Long objectId) {
        List<Tag> result = tagRepository.findByTaggedObjectId(objectId);
        return result;
    }

    public List<Tag> getTagsByObjectClass(ObjectClass objectClass) {
        List<Tag> result = tagRepository.findByTaggedObjectClass(objectClass.name());
        return result;
    }
}
