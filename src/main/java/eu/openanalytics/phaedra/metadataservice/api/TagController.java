package eu.openanalytics.phaedra.metadataservice.api;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity createTags(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.addObjectTags(taggedObject);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateTags(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.updateObjectTags(taggedObject);
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping
    public ResponseEntity deleteTags(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.removeObjectTags(taggedObject);
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}
