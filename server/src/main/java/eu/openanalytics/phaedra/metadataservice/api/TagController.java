package eu.openanalytics.phaedra.metadataservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.service.TagService;
import eu.openanalytics.phaedra.util.auth.AuthorizationHelper;

@RestController
public class  TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/tag")
    public ResponseEntity addTag(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.addObjectTag(taggedObject);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/tag")
    public ResponseEntity removeTag(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.removeObjectTag(taggedObject);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity getAllTags(@AuthenticationPrincipal Jwt accessToken) {
    	if (!AuthorizationHelper.hasAdminAccess(accessToken)) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No permission to invoke this operation");
    	}
        List<TagDTO> result = tagService.getAllTags();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(path = "/tags", params = {"objectId"})
    public ResponseEntity getTagsByObjectId(@RequestParam(value = "objectId", required = false) Long objectId) {
        List<TagDTO> result = tagService.getTagsByObjectId(objectId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(path = "/tags", params = {"objectClass"})
    public ResponseEntity getTagsByObjectId(@RequestParam(value = "objectClass", required = false) String objectClass) {
        List<TagDTO> result = tagService.getTagsByObjectClass(objectClass);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(path = "/tags", params = {"objectId", "objectClass"})
    public ResponseEntity getTagsByObjectIdAndObjectClass(@RequestParam(value = "objectId", required = false) Long objectId,
                                            @RequestParam(value = "objectClass", required = false) String objectClass) {
        List<TagDTO> result = tagService.getTagsByObjectIdAndObjectClass(objectId, objectClass);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
