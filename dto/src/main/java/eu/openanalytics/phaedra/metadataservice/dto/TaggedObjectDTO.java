package eu.openanalytics.phaedra.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaggedObjectDTO {
    private Long objectId;
    private ObjectClass objectClass;
    private String tag;
}