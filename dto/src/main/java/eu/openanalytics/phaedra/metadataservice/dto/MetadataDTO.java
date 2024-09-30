package eu.openanalytics.phaedra.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataDTO {

  private Long objectId;
  private List<TagDTO> tags;
  private List<PropertyDTO> properties;

}
