/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class ModelMapper {
    private final org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    public ModelMapper() {
        PropertyMap<Property, PropertyDTO> propertyMap = new PropertyMap <Property, PropertyDTO>() {
            protected void configure() {
                map().setPropertyName(source.getPropertyName());
                map().setObjectId(source.getObjectId());
                map().setPropertyValue(source.getPropertyValue());
                map().setObjectClass(source.getObjectClass());
            }
        };
        modelMapper.addMappings(propertyMap);

        PropertyMap<PropertyDTO, Property> propertyDTOMap = new PropertyMap <PropertyDTO, Property>() {
            protected void configure() {
                map().setPropertyName(source.getPropertyName());
                map().setObjectId(source.getObjectId());
                map().setPropertyValue(source.getPropertyValue());
                map().setObjectClass(source.getObjectClass());
                map().setId(null);
            }
        };
        modelMapper.addMappings(propertyDTOMap);

        PropertyMap<Tag, TagDTO> tagtoTagDTOMap = new PropertyMap<Tag, TagDTO>() {
            @Override
            protected void configure() {
                map().setTagId(source.getId());
                map().setTag(source.getName());
            }
        };
        modelMapper.addMappings(tagtoTagDTOMap);

        modelMapper.validate(); // ensure that objects can be mapped
    }

    PropertyDTO map(Property property) {
        return modelMapper.map(property, PropertyDTO.class);
    }

    Property map(PropertyDTO propertyDTO) {
        return modelMapper.map(propertyDTO, Property.class);
    }

    TagDTO map(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }
}
