package eu.openanalytics.phaedra.metadataservice.service;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.repository.PropertyRepository;
import org.springframework.stereotype.Service;

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

    public void deleteProperty(PropertyDTO propertyDTO) {
        Property property = propertyRepository.findByObjectIdAndPropertyNameAndObjectClass(propertyDTO.getObjectId(),
                propertyDTO.getPropertyName(), propertyDTO.getObjectClass());
        propertyRepository.delete(property);
    }

    public PropertyDTO getProperty(PropertyDTO propertyDTO) {
        Property property = propertyRepository.findByObjectIdAndPropertyNameAndObjectClass(propertyDTO.getObjectId(),
                propertyDTO.getPropertyName(), propertyDTO.getObjectClass());
        return modelMapper.map(property);
    }

    public List<PropertyDTO> getProperties(PropertyDTO propertyDTO) {
        List<Property> result = propertyRepository.findAll(propertyDTO.getObjectId(), propertyDTO.getPropertyName(), propertyDTO.getObjectClass());
        return result.stream().map(modelMapper::map)
                .collect(Collectors.toList());
    }
}
