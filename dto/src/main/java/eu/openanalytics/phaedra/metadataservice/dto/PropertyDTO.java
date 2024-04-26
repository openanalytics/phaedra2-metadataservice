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
package eu.openanalytics.phaedra.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.metadataservice.enumeration.Actor;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyDTO {
    private String propertyName;
    private String propertyValue;
    private Long objectId;
    private ObjectClass objectClass;
    private Actor actor;

    public PropertyDTO(String propertyName, Long objectId, ObjectClass objectClass) {
        this.propertyName = propertyName;
        this.objectId = objectId;
        this.objectClass = objectClass;
    }

    public PropertyDTO(String propertyName, String propertyValue, Long objectId, ObjectClass objectClass) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.objectId = objectId;
        this.objectClass = objectClass;
    }

    public PropertyDTO(String propertyName, String propertyValue, Long objectId, ObjectClass objectClass, Actor actor) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.objectId = objectId;
        this.objectClass = objectClass;
        this.actor = actor;
    }
}
