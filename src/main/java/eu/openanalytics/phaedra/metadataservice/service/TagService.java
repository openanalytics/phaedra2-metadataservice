package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public void addObjectTags(TaggedObjectDTO taggedObjectDTO) {
        taggedObjectDTO.getObjectTags().stream().forEach(tagName -> {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = tagRepository.save(new Tag(tagName));
            }
            TaggedObject taggedObject = new TaggedObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass(), tag.getId());
            tag.addObject(taggedObject);
            tagRepository.save(tag);
        });
    }

    public void updateObjectTags(TaggedObjectDTO taggedObject) {
        //TODO
    }

    public void removeObjectTags(TaggedObjectDTO taggedObjectDTO) {
        taggedObjectDTO.getObjectTags().stream().forEach(tagName -> {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag(tagName);
            }

            tag.removeObject(taggedObjectDTO.getObjectId(), taggedObjectDTO.getObjectClass());
            tagRepository.save(tag);
        });
    }
}
