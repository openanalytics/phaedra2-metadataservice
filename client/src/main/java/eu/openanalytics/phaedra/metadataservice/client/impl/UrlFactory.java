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
package eu.openanalytics.phaedra.metadataservice.client.impl;

public class UrlFactory {

    private static final String METADATA_SERVICE = "http://phaedra-metadata-service:8080/phaedra/metadata-service";

    public static String tags(String objectClass, long objectId) {
        return String.format("%s/tags?objectId=%s&objectClass=%s", METADATA_SERVICE, objectId, objectClass);
    }

    public static String properties(String objectClass, long objectId) {
        return String.format("%s/properties?objectId=%s&objectClass=%s", METADATA_SERVICE, objectId, objectClass);
    }

    public static String tags() {
        return String.format("%s/tags", METADATA_SERVICE);
    }

    public static String properties() {
        return String.format("%s/properties", METADATA_SERVICE);
    }


}
