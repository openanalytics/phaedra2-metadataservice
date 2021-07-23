package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public void createProperty(Property property) {
        propertyRepository.save(property);
    }

    public void deleteProperty(Property property) {
        propertyRepository.delete(property);
    }

    public void updateProperty(Property property) {
        propertyRepository.save(property);
    }

    public List<Property> getPropertiesByObjectId(Long objectId) {
        return propertyRepository.findAllByObjectId(objectId);
    }

    public Property getPropertyByPropertyNameAndObjectId(String propertyName, Long objectId) {
        return null;
    }

    public List<PropertyDTO> getAvailableProperties() {
        List<PropertyDTO> result = propertyRepository.findAvailableProperties().stream()
                .map(p -> {
                    PropertyDTO propertyDTO = new PropertyDTO(p.getPropertyName(), p.getObjectClass());
                    return propertyDTO;
                }).collect(Collectors.toList());
        return result;
    }

    public List<PropertyDTO> getAvailablePropertiesByObjectClass(String objectClass) {
        List<PropertyDTO> result = propertyRepository.findAvailablePropertiesByObjectClass(objectClass).stream()
                .map(p -> {
                    PropertyDTO propertyDTO = new PropertyDTO(p.getPropertyName(), p.getObjectClass());
                    return propertyDTO;
                }).collect(Collectors.toList());
        return result;
    }

    public List<PropertyDTO> getAvailablePropertiesByPropertyName(String propertyName) {
        List<PropertyDTO> result = propertyRepository.findAvailablePropertiesByByPropertyName(propertyName).stream()
                .map(p -> {
                    PropertyDTO propertyDTO = new PropertyDTO(p.getPropertyName(), p.getObjectClass());
                    return propertyDTO;
                }).collect(Collectors.toList());
        return result;
    }
}
