/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import eu.openanalytics.phaedra.metadataservice.service.TagService;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

	@Autowired
    private TagService tagService;
	
	@Autowired
    private PropertyService propertyService;
	
    @GetMapping
    public ResponseEntity<?> getMetadata(
    		@RequestParam(value = "objectClass") String objectClass,
    		@RequestParam(value = "objectId") String objectId) {
    	
    	Set<Long> objectIds = Arrays.stream(objectId.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toSet());
    	if (objectIds.isEmpty()) return ResponseEntity.badRequest().body("Must specify at least one object ID");
    	
    	Map<Long, List<TagDTO>> tagsPerObject = tagService.getTagsByObjectIdsAndObjectClass(objectIds, objectClass);
    	Map<Long, List<PropertyDTO>> propertiesPerObject = propertyService.getProperties(objectIds, objectClass);
    	
    	List<Map<?,?>> response = new ArrayList<>();
    	for (long id: objectIds) {
    		Map<String, Object> objectMetadata = new HashMap<>();
    		objectMetadata.put("objectId", id);
    		objectMetadata.put("objectClass", objectClass);
    		if (tagsPerObject.containsKey(id)) objectMetadata.put("tags", tagsPerObject.get(id));
    		if (propertiesPerObject.containsKey(id)) objectMetadata.put("properties", propertiesPerObject.get(id));
    		response.add(objectMetadata);
    	}
        return ResponseEntity.ok(response);
    }
}
