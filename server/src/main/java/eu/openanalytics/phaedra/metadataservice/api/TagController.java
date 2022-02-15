/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.metadataservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.service.TagService;

@RestController
public class  TagController {
	
    @Autowired
    private TagService tagService;

    @PostMapping("/tag")
    public ResponseEntity<?> addTag(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.addObjectTag(taggedObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/tag")
    public ResponseEntity<?> removeTag(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.removeObjectTag(taggedObject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity<?> getAllTags() {
        List<TagDTO> result = tagService.getAllTags();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/tags", params = {"objectClass"})
    public ResponseEntity<?> getTagsByObjectClass(@RequestParam(value = "objectClass", required = false) String objectClass) {
        List<TagDTO> result = tagService.getTagsByObjectClass(objectClass);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/tags", params = {"objectId", "objectClass"})
    public ResponseEntity<?> getTagsByObjectIdAndObjectClass(@RequestParam(value = "objectId", required = false) Long objectId,
                                            @RequestParam(value = "objectClass", required = false) String objectClass) {
        List<TagDTO> result = tagService.getTagsByObjectIdAndObjectClass(objectId, objectClass);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
