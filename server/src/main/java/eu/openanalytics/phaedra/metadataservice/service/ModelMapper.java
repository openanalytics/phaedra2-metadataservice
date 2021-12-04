package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.model.Property;
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

        modelMapper.validate(); // ensure that objects can be mapped
    }

    PropertyDTO map(Property property) {
        return modelMapper.map(property, PropertyDTO.class);
    }

    Property map(PropertyDTO propertyDTO) {
        return modelMapper.map(propertyDTO, Property.class);
    }
}
