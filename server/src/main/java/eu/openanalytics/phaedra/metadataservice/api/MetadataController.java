package eu.openanalytics.phaedra.metadataservice.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
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
    	
    	Set<Long> objectIds = Arrays.stream(objectId.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toSet());
    	if (objectIds.isEmpty()) return ResponseEntity.badRequest().body("Must specify at least one object ID");
    	
    	Map<Long, List<TagDTO>> tagsPerObject = tagService.getTagsByObjectIdsAndObjectClass(objectIds, objectClass);
    	Map<Long, List<PropertyDTO>> propertiesPerObject = propertyService.getProperties(objectIds, objectClass);
    	
    	List<Map<?,?>> response = new ArrayList<>();
    	for (long id: objectIds) {
    		Map<String, Object> objectMetadata = new HashMap<>();
    		objectMetadata.put("objectId", id);
    		objectMetadata.put("objectClass", objectClass);
    		if (tagsPerObject.containsKey(id)) objectMetadata.put("tags", tagsPerObject.get(id));
    		if (propertiesPerObject.containsKey(id)) objectMetadata.put("properties", propertiesPerObject.get(id));
    		response.add(objectMetadata);
    	}
        return ResponseEntity.ok(response);
    }
}
