package eu.openanalytics.phaedra.metadataservice.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.service.PropertyService;
import eu.openanalytics.phaedra.metadataservice.service.TagService;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

	@Autowired
    private TagService tagService;
	
	@Autowired
    private PropertyService propertyService;
	
    @GetMapping
    public ResponseEntity<?> getMetadata(
    		@RequestParam(value = "objectClass") String objectClass,
    		@RequestParam(value = "objectId") String objectId) {
    	
    	List<Map<?,?>> response = new ArrayList<>();
    	
    	long[] objectIds = Arrays.stream(objectId.split(",")).mapToLong(id -> Long.parseLong(id)).toArray();
    	
    	for (long id: objectIds) {
    		Map<String, Object> objectMetadata = new HashMap<>();
    		
    		objectMetadata.put("objectId", id);
    		objectMetadata.put("objectClass", objectClass);
    		objectMetadata.put("tags", tagService.getTagsByObjectIdAndObjectClass(id, objectClass));
    		objectMetadata.put("properties", propertyService.getProperties(new PropertyDTO(null, id, objectClass)));
    		
    		response.add(objectMetadata);
    	}
    	
        return ResponseEntity.ok(response);
    }
}
