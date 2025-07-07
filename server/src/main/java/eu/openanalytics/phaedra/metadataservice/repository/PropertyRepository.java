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
import eu.openanalytics.phaedra.metadataservice.model.Property;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query("select * from hca_property as p " +
            "where p.object_id = :objectId " +
            "and p.property_name = :propertyName " +
            "and p.object_class = cast(:objectClass as objectclass)")
    Property findByObjectIdAndPropertyNameAndObjectClass(Long objectId, String propertyName, ObjectClass objectClass);

    @Query("select * from hca_property as p " +
            "where (:objectId is null or p.object_id = :objectId) " +
            "and (:propertyName is null or p.property_name = :propertyName) " +
            "and (:objectClass is null or p.object_class = cast(:objectClass as objectclass))")
    List<Property> findAll(Long objectId, String propertyName, ObjectClass objectClass);

    @Query("select * from hca_property as p " +
            "where (p.object_id in (:objectId)) " +
            "and (:objectClass is null or p.object_class = cast(:objectClass as objectclass))")
    List<Property> findByObjectIdInAndObjectClass(Set<Long> objectId, ObjectClass objectClass);

}
