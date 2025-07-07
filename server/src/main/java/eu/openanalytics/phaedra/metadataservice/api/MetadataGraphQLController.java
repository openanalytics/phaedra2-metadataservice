/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
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
import org.springframework.graphql.data.method.annotation.Argument;
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
  public List<MetadataDTO> metadata(@Argument List<Long> objectIds, @Argument ObjectClass objectClass) {
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
