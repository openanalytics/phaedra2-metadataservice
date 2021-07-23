package eu.openanalytics.phaedra.metadataservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TaggedObjectDTO {
    private Long objectId;
    private String objectClass;
    private Set<String> objectTags;
}
