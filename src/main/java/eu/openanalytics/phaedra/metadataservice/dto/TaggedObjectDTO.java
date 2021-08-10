package eu.openanalytics.phaedra.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaggedObjectDTO {
    private Long objectId;
    private ObjectClass objectClass;
    private String tag;
}
