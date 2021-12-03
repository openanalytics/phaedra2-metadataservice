package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyFilterDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class PropertyController {

    @Autowired
    private PropertyService metadataService;

    /**
     * Create new property for a specific object
     * @param propertyDTO
     */
    @PostMapping("/properties")
    public ResponseEntity createProperty(@RequestBody @Valid PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setPropertyName(propertyDTO.getPropertyName());
        property.setPropertyValue(propertyDTO.getPropertyValue());
        property.setObjectId(propertyDTO.getObjectId());
        property.setObjectClass(propertyDTO.getObjectClass().name());
        metadataService.createProperty(property);
        return new ResponseEntity(property, HttpStatus.CREATED);
    }

    /**
     * Delete an existing property
     * @param property
     */
    @DeleteMapping("/properties")
    public ResponseEntity deleteProperty(@RequestBody @Valid Property property) {
        metadataService.deleteProperty(property);
        return new ResponseEntity(property, HttpStatus.OK);
    }

    /**
     * Update a property
     * @param property
     */
    @PutMapping("/properties")
    public ResponseEntity updateProperty(@RequestBody @Valid Property property) {
        metadataService.updateProperty(property);
        return new ResponseEntity(property, HttpStatus.OK);
    }

    /**
     * Get all properties filtered by the request pqrameters
     * @param propertyName The property name (Optional)
     * @param objectId The object id (Optional)
     * @param objectClass The object class (Optional)
     * @return Properties value
     */
    @GetMapping(path = "/properties", params = {"propertyName", "objectId", "objectClass"})
    public ResponseEntity getProperties(@RequestParam(value = "propertyName", required = false) String propertyName,
                                       @RequestParam(value = "objectId", required = false) Long objectId,
                                       @RequestParam(value = "objectClass", required = false) ObjectClass objectClass) {
        PropertyFilterDTO propertyFilter = new PropertyFilterDTO(propertyName, objectId, objectClass);
        List<PropertyDTO> result = metadataService.getProperties(propertyFilter);
        return new ResponseEntity(result, HttpStatus.OK);
    }
 }
