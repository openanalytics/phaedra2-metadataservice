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
package eu.openanalytics.phaedra.metadataservice.client.config;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceGraphQlClient;
import eu.openanalytics.phaedra.metadataservice.client.impl.MetaDataServiceHttpGraphQlClient;
import eu.openanalytics.phaedra.metadataservice.client.impl.HttpMetadataServiceClient;
import eu.openanalytics.phaedra.util.PhaedraRestTemplate;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class MetadataServiceClientAutoConfiguration {

    @Bean
    public MetadataServiceClient metadataServiceClient(PhaedraRestTemplate phaedraRestTemplate, IAuthorizationService authService, Environment environment) {
        return new HttpMetadataServiceClient(phaedraRestTemplate, authService, environment);
    }

    @Bean
    public MetadataServiceGraphQlClient metadataServiceGraphQLClient(IAuthorizationService authService, Environment environment) {
        return new MetaDataServiceHttpGraphQlClient(authService, environment);
    }
}
