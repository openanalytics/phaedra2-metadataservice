package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.service.TaggedObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/tagged_objects")
public class TaggedObjectController {
    @Autowired
    private TaggedObjectService taggedObjectService;

    @GetMapping("/{objectClass}")
    public ResponseEntity getAllTaggedObjectsByObjectClass(@PathVariable("objectClass") ObjectClass objectClass) {
        List<TaggedObjectDTO> response = new ArrayList<>();
        response.addAll(taggedObjectService.findAllTaggedObjectsByObjectClass(objectClass));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{objectClass}", params = {"objectId"})
    public ResponseEntity getTaggedObjectByIdAndClass(@PathVariable("objectClass") ObjectClass objectClass,
                                                      @RequestParam(value = "objectId", required = false) Long objectId) {
        List<TaggedObjectDTO> response = new ArrayList<>();
        response.addAll(taggedObjectService.findTaggedObjectByObjectIdAndObjectClass(objectId, objectClass));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{objectClass}", params = {"tag"})
    public ResponseEntity getTaggedObjectsByTag(@PathVariable("objectClass") ObjectClass objectClass,
                                                @RequestParam(value = "tag", required = false) String tag) {
        List<TaggedObjectDTO> response = taggedObjectService.findAllTaggedObjectsByTagAndObjectClass(tag, objectClass);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
