package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.MetadataDTO;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import eu.openanalytics.phaedra.metadataservice.service.TagService;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MetadataGraphQLController {

  private final TagService tagService;
  private final PropertyService propertyService;

  public MetadataGraphQLController(TagService tagService, PropertyService propertyService) {
    this.tagService = tagService;
    this.propertyService = propertyService;
  }

  @QueryMapping
  public List<MetadataDTO> metadata(List<Long> objectIds, ObjectClass objectClass) {
    Set<Long> objectIdsSet = new HashSet<>(objectIds);
    Map<Long, List<TagDTO>> tags = tagService.getTags(objectIdsSet, objectClass);
    Map<Long, List<PropertyDTO>> properties = propertyService.getProperties(objectIdsSet, objectClass);

    return objectIds.parallelStream()
        .map(objectId -> new MetadataDTO(
            objectId,
            tags.getOrDefault(objectId, Collections.emptyList()),
            properties.getOrDefault(objectId, Collections.emptyList())
        ))
        .toList();
  }
}
