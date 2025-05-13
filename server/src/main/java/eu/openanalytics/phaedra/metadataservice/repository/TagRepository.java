/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
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

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String tagName);

    List<Tag> findByIdIn(Set<Long> ids);

    @Query("select ht.* from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_id = :objectId")
    List<Tag> findByObjectId(Long objectId);

    @Query("select ht.* from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_class = cast(:objectClass as objectclass)")
    List<Tag> findByObjectClass(ObjectClass objectClass);

    @Query("select ht.* from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_id = :objectId " +
            "and hto.object_class = cast(:objectClass as objectclass)")
    List<Tag> findByObjectIdAndObjectClass(Long objectId, ObjectClass objectClass);

}
