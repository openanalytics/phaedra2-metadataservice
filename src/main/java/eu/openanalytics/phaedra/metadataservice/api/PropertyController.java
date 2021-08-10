package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
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
     * Get all properties for a specific object
     * @param objectId The id of the object
     * @return List of properties for a given object
     */
    @GetMapping(path = "/properties", params = {"objectId"})
    public ResponseEntity getPropertiesByObjectId(@RequestParam(value = "objectId", required = false) Long objectId) {
        List<Property> result = metadataService.getPropertiesByObjectId(objectId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get a property value for specific property and object
     * @param propertyName The property name
     * @param objectId The object id
     * @return Property value for a given property and object
     */
    @GetMapping(path = "/properties", params = {"propertyName", "objectId"})
    public ResponseEntity getPropertyByPropertyNameAndObjectId(@RequestParam(value = "propertyName", required = false) String propertyName, @RequestParam(value = "objectId", required = false) Long objectId) {
        Property result = metadataService.getPropertyByPropertyNameAndObjectId(propertyName, objectId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get all available properties
     * @return
     */
    @GetMapping("/properties")
    public ResponseEntity getAvailableProperties() {
        List<PropertyDTO> result = metadataService.getAvailableProperties();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get available properties for a specific object class
     * @param objectClass
     * @return
     */
    @GetMapping(path = "/properties", params = {"objectClass"})
    public ResponseEntity getAvailablePropertiesByObjectClass(@RequestParam(value = "objectClass", required = false) ObjectClass objectClass) {
        List<PropertyDTO> result = metadataService.getAvailablePropertiesByObjectClass(objectClass.name());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get available properties with the same property name
     * @param propertyName
     * @return
     */
    @GetMapping(path = "/properties", params = {"propertyName"})
    public ResponseEntity getAvailablePropertiesByPropertyName(@RequestParam(value = "propertyName", required = false) String propertyName) {
        List<PropertyDTO> result = metadataService.getAvailablePropertiesByPropertyName(propertyName);
        return new ResponseEntity(result, HttpStatus.OK);
    }
 }
