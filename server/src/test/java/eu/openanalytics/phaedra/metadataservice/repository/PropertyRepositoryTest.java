/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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

import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.support.Containers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", Containers.postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USER", Containers.postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", Containers.postgreSQLContainer::getPassword);
    }

    @Test
    public void contextLoads() {
        assertThat(propertyRepository).isNotNull();
    }

    @Test
    public void getProperties() {
        Iterable<Property> allProperties = propertyRepository.findAll();
        assertThat(allProperties).isNotNull();
        assertThat(allProperties).isNotEmpty();
    }

    @Test
    public void queryProperties1() {
        List<Property> filteredProperties = propertyRepository.findAll(1000L, null, null);
        assertThat(filteredProperties).isNotNull();
        assertThat(filteredProperties).isNotEmpty();
        assertThat(filteredProperties.size() == 6).isTrue();
    }

    @Test
    public void queryProperties2() {
        List<Property> result1 = propertyRepository.findAll(null, null, "PROTOCOL");
        assertThat(result1).isNotNull();
        assertThat(result1).isNotEmpty();
        assertThat(result1.size() == 3).isTrue();

        List<Property> result2 = propertyRepository.findAll(null, null, "PROJECT");
        assertThat(result2).isNotNull();
        assertThat(result2).isNotEmpty();
        assertThat(result2.size() == 3).isTrue();

        List<Property> result3 = propertyRepository.findAll(null, null, "WELL");
        assertThat(result3).isNotNull();
        assertThat(result3).isNotEmpty();
        assertThat(result3.size() == 3).isTrue();
    }

    @Test
    public void queryProperties3() {
        List<Property> filteredProperties = propertyRepository.findAll(null, "NumberOfFeatures", null);
        assertThat(filteredProperties).isNotNull();
        assertThat(filteredProperties).isNotEmpty();
        assertThat(filteredProperties.size() == 3).isTrue();
    }

    @Test
    public void queryProperties4() {
        List<Property> filteredProperties = propertyRepository.findAll(null, null, null);
        assertThat(filteredProperties).isNotNull();
        assertThat(filteredProperties).isNotEmpty();
    }
}
