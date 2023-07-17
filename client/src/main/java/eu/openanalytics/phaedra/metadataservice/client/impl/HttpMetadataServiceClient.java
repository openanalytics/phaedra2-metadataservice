/**
 * Phaedra II
 * <p>
 * Copyright (C) 2016-2023 Open Analytics
 * <p>
 * ===========================================================================
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 * <p>
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.metadataservice.client.impl;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class HttpMetadataServiceClient implements MetadataServiceClient {

    private final RestTemplate restTemplate;
    private final IAuthorizationService authService;

    public HttpMetadataServiceClient(RestTemplate restTemplate, IAuthorizationService authService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
    }

    @Override
    public List<TagDTO> getTags(ObjectClass objectClass, long objectId) {
        var response = restTemplate.exchange(UrlFactory.tags(objectClass, objectId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), TagDTO[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public List<PropertyDTO> getPorperties(ObjectClass objectClass, long objectId) {
        var response = restTemplate.exchange(UrlFactory.properties(objectClass, objectId), HttpMethod.GET, new HttpEntity<String>(makeHttpHeaders()), PropertyDTO[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    private HttpHeaders makeHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String bearerToken = authService.getCurrentBearerToken();
        if (bearerToken != null) httpHeaders.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken));
        return httpHeaders;
    }
}
