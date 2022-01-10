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
public class PropertyDTO {
    private String propertyName;
    private String propertyValue;
    private Long objectId;
    private String objectClass;

    public PropertyDTO(String propertyName, Long objectId, String objectClass) {
        this.propertyName = propertyName;
        this.objectId = objectId;
        this.objectClass = objectClass;
    }
}
