/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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

import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class  TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<?> addTag(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.addObjectTag(taggedObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> removeTag(@RequestBody TaggedObjectDTO taggedObject) {
        tagService.removeObjectTag(taggedObject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTagsByObjectIdAndObjectClass(
    		@RequestParam(value = "objectId", required = false) Long objectId,
    		@RequestParam(value = "objectClass", required = false) String objectClass) {

    	List<TagDTO> tags = null;

    	if (objectId == null) {
    		if (objectClass == null) {
    			tags = tagService.getAllTags();
    		} else {
    			tags = tagService.getTagsByObjectClass(objectClass);
    		}
    	} else {
    		tags = tagService.getTagsByObjectIdAndObjectClass(objectId, objectClass);
    	}

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
