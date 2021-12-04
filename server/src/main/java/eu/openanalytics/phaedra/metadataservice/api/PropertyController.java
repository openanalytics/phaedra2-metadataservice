package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/property")
    public ResponseEntity createProperty(@RequestBody @Valid PropertyDTO propertyDTO) {
        PropertyDTO newProperty = metadataService.createProperty(propertyDTO);
        return new ResponseEntity(newProperty, HttpStatus.CREATED);
    }

    /**
     * Delete an existing property
     * @param property
     */
    @DeleteMapping("/property")
    public ResponseEntity deleteProperty(@RequestBody @Valid PropertyDTO property) {
        metadataService.deleteProperty(property);
        return new ResponseEntity(property, HttpStatus.OK);
    }

    /**
     * Update a property
     * @param property
     */
    @PutMapping("/property")
    public ResponseEntity updateProperty(@RequestBody @Valid PropertyDTO property) {
        PropertyDTO updated = metadataService.updateProperty(property);
        return new ResponseEntity(updated, HttpStatus.OK);
    }

    /**
     * Update a property
     * @param property
     */
    @GetMapping("/property")
    public ResponseEntity getProperty(@RequestBody @Valid PropertyDTO property) {
        PropertyDTO existing = metadataService.getProperty(property);
        return new ResponseEntity(existing, HttpStatus.OK);
    }

    /**
     * Get all properties filtered by the request pqrameters
     * @param propertyName The property name (Optional)
     * @param objectId The object id (Optional)
     * @param objectClass The object class (Optional)
     * @return Properties value
     */
    @GetMapping(path = "/properties")
    public ResponseEntity getProperties(@RequestParam(value = "propertyName", required = false) String propertyName,
                                       @RequestParam(value = "objectId", required = false) Long objectId,
                                       @RequestParam(value = "objectClass", required = false) ObjectClass objectClass) {
        PropertyDTO propertyFilter = new PropertyDTO();
        propertyFilter.setPropertyName(propertyName);
        propertyFilter.setObjectId(objectId);
        propertyFilter.setObjectClass(objectClass);

        List<PropertyDTO> result = metadataService.getProperties(propertyFilter);
        return new ResponseEntity(result, HttpStatus.OK);
    }
 }
