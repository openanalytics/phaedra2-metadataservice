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
package eu.openanalytics.phaedra.metadataservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;

@Repository
public interface TaggedObjectRepository extends CrudRepository<TaggedObject, Long> {
	
	@Modifying
	@Query("delete from hca_tagged_object hto where hto.tag_id = :tagId and hto.object_id = :objectId and hto.object_class = :objectClass")
	void deleteByTagIdAndObjectIdAndObjectClass(Long tagId, Long objectId, String objectClass);
	
    List<TaggedObject> findByObjectIdInAndObjectClass(Set<Long> objectIds, String objectClass);
}
