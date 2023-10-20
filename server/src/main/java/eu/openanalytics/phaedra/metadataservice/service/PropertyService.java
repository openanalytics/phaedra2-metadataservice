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
package eu.openanalytics.phaedra.metadataservice.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.repository.PropertyRepository;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property newProperty = modelMapper.map(propertyDTO);
        return modelMapper.map(propertyRepository.save(newProperty));
    }

    public PropertyDTO updateProperty(PropertyDTO propertyDTO) {
        Property updatedProperty = modelMapper.map(propertyDTO);
        Property existing = propertyRepository.findByObjectIdAndPropertyNameAndObjectClass(propertyDTO.getObjectId(),
                propertyDTO.getPropertyName(), propertyDTO.getObjectClass());

        updatedProperty.setId(existing.getId());
        return modelMapper.map(propertyRepository.save(updatedProperty));
    }

    public PropertyDTO deleteProperty(String propertyName, Long objectId, String objectClass) {
        Property property = propertyRepository.findByObjectIdAndPropertyNameAndObjectClass(objectId, propertyName, objectClass);
        propertyRepository.delete(property);
        return modelMapper.map(property);
    }

    public PropertyDTO getProperty(String propertyName, Long objectId, String objectClass) {
        Property property = propertyRepository.findByObjectIdAndPropertyNameAndObjectClass(objectId, propertyName, objectClass);
        return modelMapper.map(property);
    }

    public List<PropertyDTO> getProperties(PropertyDTO propertyDTO) {
        List<Property> result = propertyRepository.findAll(propertyDTO.getObjectId(), propertyDTO.getPropertyName(), propertyDTO.getObjectClass());
        return result.stream().map(modelMapper::map)
                .collect(Collectors.toList());
    }

    public Map<Long, List<PropertyDTO>> getProperties(Set<Long> objectIds, String objectClass) {
    	List<Property> props = propertyRepository.findByObjectIdInAndObjectClass(objectIds, objectClass);
    	return props.stream().map(modelMapper::map).collect(Collectors.groupingBy(PropertyDTO::getObjectId));
    }
}
