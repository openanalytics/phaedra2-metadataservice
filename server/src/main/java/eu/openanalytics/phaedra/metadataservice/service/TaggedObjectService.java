package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.repository.TagRepository;
import eu.openanalytics.phaedra.metadataservice.repository.TaggedObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaggedObjectService {
    @Autowired
    private TaggedObjectRepository taggedObjectRepository;
    @Autowired
    private TagRepository tagRepository;

    public List<TaggedObjectDTO> findAllTaggedObjectsByObjectClass(ObjectClass objectClass) {
        List<TaggedObject> result = taggedObjectRepository.findAllByObjectClass(objectClass.name());
        return result.stream().map(to -> mapToTaggedObjectDTO(to)).collect(Collectors.toList());
    }

    public List<TaggedObjectDTO> findTaggedObjectByObjectIdAndObjectClass(Long objectId, ObjectClass objectClass) {
        List<TaggedObject> result = taggedObjectRepository.findTaggedObjectByObjectIdAndObjectClass(objectId, objectClass.name());
        return result.stream().map(to -> mapToTaggedObjectDTO(to)).collect(Collectors.toList());
    }

    /**
     * Find all TaggedObjects by a tag
     * @param tagName A tag string
     * @return List of tagged objects
     */
    public List<TaggedObjectDTO> findAllTaggedObjectsByTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName);
        if (tag != null) {
            return tag.getTaggedObjects().stream()
                    .map(to -> mapToTaggedObjectDTO(to))
                    .collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Find all TaggedObjects by a tag and an object class
     * @param tagName A tag string
     * @param objectClass WELL, PLATE, FEATURE, PROTOCOL, EXPERIMENT or PROJECT
     * @return List of tagged objects
     */
    public List<TaggedObjectDTO> findAllTaggedObjectsByTagAndObjectClass(String tagName, ObjectClass objectClass) {
        Tag tag = tagRepository.findByName(tagName);
        if (tag != null) {
            return tag.getTaggedObjects().stream()
                    .filter(to -> to.getObjectClass().equals(objectClass.name()))
                    .map(to -> mapToTaggedObjectDTO(to))
                    .collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private TaggedObjectDTO mapToTaggedObjectDTO(TaggedObject taggedObject) {
        TaggedObjectDTO taggedObjectDTO = new TaggedObjectDTO();
        taggedObjectDTO.setObjectId(taggedObject.getObjectId());
        taggedObjectDTO.setObjectClass(ObjectClass.valueOf(taggedObject.getObjectClass()));

        Optional<Tag> tag = tagRepository.findById(taggedObject.getTagId());
        taggedObjectDTO.setTag(tag.map(Tag::getName).orElse(null));

        return taggedObjectDTO;
    }
}
