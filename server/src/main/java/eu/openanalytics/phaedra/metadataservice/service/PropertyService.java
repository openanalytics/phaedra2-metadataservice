package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyFilterDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public void createProperty(Property property) {
        propertyRepository.save(property);
    }

    public void deleteProperty(Property property) {
        propertyRepository.delete(property);
    }

    public void updateProperty(Property property) {
        propertyRepository.save(property);
    }

    public List<PropertyDTO> getProperties(PropertyFilterDTO filterDTO) {
        List<Property> result = propertyRepository.findAll(filterDTO.getObjectId(), filterDTO.getPropertyName(), filterDTO.getObjectClass());
        return result.stream().map(modelMapper::map)
                .collect(Collectors.toList());
    }
}
