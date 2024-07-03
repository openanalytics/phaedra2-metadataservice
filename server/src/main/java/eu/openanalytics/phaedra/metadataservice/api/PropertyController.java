/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService metadataService;

    public PropertyController(PropertyService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping
    public ResponseEntity<?> createProperty(@RequestBody @Valid PropertyDTO propertyDTO) {
        PropertyDTO newProperty = metadataService.createProperty(propertyDTO);
        return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProperty(@RequestParam(value = "propertyName") String propertyName,
                                         @RequestParam(value = "objectId") Long objectId,
                                         @RequestParam(value = "objectClass") ObjectClass objectClass) {
        PropertyDTO deletedProperty = metadataService.deleteProperty(propertyName, objectId, objectClass);
        return new ResponseEntity<>(deletedProperty, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProperty(@RequestBody @Valid PropertyDTO property) {
        PropertyDTO updated = metadataService.updateProperty(property);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getProperties(
    		@RequestParam(value = "propertyName", required = false) String propertyName,
    		@RequestParam(value = "objectId", required = false) Long objectId,
    		@RequestParam(value = "objectClass", required = false) ObjectClass objectClass) {

        PropertyDTO propertyFilter = new PropertyDTO();
        propertyFilter.setPropertyName(propertyName);
        propertyFilter.setObjectId(objectId);
        propertyFilter.setObjectClass(objectClass);

        return new ResponseEntity<>(metadataService.getProperties(propertyFilter), HttpStatus.OK);
    }
 }
