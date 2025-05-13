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
package eu.openanalytics.phaedra.metadataservice.client.impl;

import eu.openanalytics.phaedra.metadataservice.dto.MetadataDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.WebGraphQlClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

@Component
public class HttpMetadataServiceClient implements MetadataServiceClient {

    private final RestTemplate restTemplate;
    private final IAuthorizationService authService;

    private final UrlFactory urlFactory;

    private static final String PROP_BASE_URL = "phaedra.metadata-service.base-url";
    private static final String DEFAULT_BASE_URL = "http://phaedra-metadata-service:8080/phaedra/metadata-service";

    public HttpMetadataServiceClient(RestTemplate restTemplate, IAuthorizationService authService, Environment environment) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.urlFactory = new UrlFactory(environment.getProperty(PROP_BASE_URL, DEFAULT_BASE_URL));
    }

    @Override
    public void addTags(String objectClass, long objectId, List<String> tags) {
        tags.stream()
                .map(tag -> TaggedObjectDTO.builder()
                        .objectId(objectId)
                        .objectClass(ObjectClass.valueOf(objectClass))
                        .tag(tag)
                        .build())
                .forEach(taggedObjectDTO -> restTemplate.exchange(urlFactory.tags(), HttpMethod.POST, new HttpEntity<>(taggedObjectDTO, makeHttpHeaders()), Void.class));
    }

    @Override
    public void addProperties(String objectClass, long objectId, Map<String, String> properties) {
        properties.keySet().stream()
                .map(propertyName -> new PropertyDTO(propertyName, properties.get(propertyName), objectId, ObjectClass.valueOf(objectClass)))
                .forEach(propertyDTO -> restTemplate.exchange(urlFactory.properties(), HttpMethod.POST, new HttpEntity<>(propertyDTO, makeHttpHeaders()), PropertyDTO.class));
    }

    @Override
    public List<TagDTO> getTags(String objectClass, long objectId) {
        var response = restTemplate.exchange(urlFactory.tags(objectClass, objectId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), TagDTO[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public List<PropertyDTO> getProperties(String objectClass, long objectId) {
        var response = restTemplate.exchange(urlFactory.properties(objectClass, objectId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), PropertyDTO[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public List<MetadataDTO> getMetadata(List<Long> objectIds, ObjectClass objectClass) {
        return List.of();
    }

    private HttpHeaders makeHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String bearerToken = authService.getCurrentBearerToken();
        if (bearerToken != null) httpHeaders.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken));
        return httpHeaders;
    }
}
