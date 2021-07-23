package eu.openanalytics.phaedra.metadataservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyDTO {
    private String propertyName;
    private String objectClass;
}
