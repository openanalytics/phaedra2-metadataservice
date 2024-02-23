/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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
import eu.openanalytics.phaedra.metadataservice.service.TagService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagGraphQLController {

    private final TagService tagService;

    public TagGraphQLController(TagService tagService) {
        this.tagService = tagService;
    }

//    public ResponseEntity<?> addTag(@RequestBody TaggedObjectDTO taggedObject) {
//        tagService.addObjectTag(taggedObject);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

//    public ResponseEntity<?> removeTag(@RequestBody TaggedObjectDTO taggedObject) {
//        tagService.removeObjectTag(taggedObject);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @QueryMapping
//    public List<TagDTO> getTagsByObjectId(@Argument long objectId) {
//        List<TagDTO> result = tagService.getTagsByObjectId(objectId);
//        return result;
//    }

    @QueryMapping
    public List<TagDTO> getTagsByObjectIdAndObjectClass(@Argument long objectId, @Argument String objectClass) {
    	List<TagDTO> result = tagService.getTagsByObjectIdAndObjectClass(objectId, objectClass);
        return result;
    }
}
