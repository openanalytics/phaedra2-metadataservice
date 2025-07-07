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
package eu.openanalytics.phaedra.metadataservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import eu.openanalytics.phaedra.metadataservice.enumeration.Actor;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("hca_tag")
public class Tag {

	@Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Builder.Default
    private Actor actor = Actor.USER;
    @NotNull
    @Builder.Default
    private String color = "#4caf50";
}
