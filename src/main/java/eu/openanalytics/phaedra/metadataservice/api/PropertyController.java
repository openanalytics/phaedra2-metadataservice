package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("properties")
public class PropertyController {

    @Autowired
    private PropertyService metadataService;

    /**
     * Create new property for a specific object
     * @param property
     */
    @PostMapping()
    public ResponseEntity createProperty(@RequestBody @Valid Property property) {
        metadataService.createProperty(property);
        return new ResponseEntity(property, HttpStatus.CREATED);
    }

    /**
     * Delete an existing property
     * @param property
     */
    @DeleteMapping()
    public ResponseEntity deleteProperty(@RequestBody @Valid Property property) {
        metadataService.deleteProperty(property);
        return new ResponseEntity(property, HttpStatus.OK);
    }

    /**
     * Update a property
     * @param property
     */
    @PutMapping()
    public ResponseEntity updateProperty(@RequestBody @Valid Property property) {
        metadataService.updateProperty(property);
        return new ResponseEntity(property, HttpStatus.OK);
    }

    /**
     * Get all properties for a specific object
     * @param objectId The id of the object
     * @return List of properties for a given object
     */
    @GetMapping(params = {"objectId"})
    public ResponseEntity getPropertiesByObjectId(@RequestParam("objectId") Long objectId) {
        List<Property> result = metadataService.getPropertiesByObjectId(objectId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get a property value for specific property and object
     * @param propertyName The property name
     * @param objectId The object id
     * @return Property value for a given property and object
     */
    @GetMapping(params = {"propertyName", "objectId"})
    public ResponseEntity getPropertyByPropertyNameAndObjectId(@RequestParam("propertyName") String propertyName, @RequestParam("objectId") Long objectId) {
        Property result = metadataService.getPropertyByPropertyNameAndObjectId(propertyName, objectId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get all available properties
     * @return
     */
    @GetMapping()
    public ResponseEntity getAvailableProperties() {
        List<PropertyDTO> result = metadataService.getAvailableProperties();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get available properties for a specific object class
     * @param objectClass
     * @return
     */
    @GetMapping(params = {"objectClass"})
    public ResponseEntity getAvailablePropertiesByObjectClass(@RequestParam("objectClass") String objectClass) {
        List<PropertyDTO> result = metadataService.getAvailablePropertiesByObjectClass(objectClass);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get available properties with the same property name
     * @param propertyName
     * @return
     */
    @GetMapping(params = {"propertyName"})
    public ResponseEntity getAvailablePropertiesByPropertyName(@RequestParam("propertyName") String propertyName) {
        List<PropertyDTO> result = metadataService.getAvailablePropertiesByPropertyName(propertyName);
        return new ResponseEntity(result, HttpStatus.OK);
    }
 }
