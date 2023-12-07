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

import eu.openanalytics.phaedra.metadataservice.model.Tag;
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
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", Containers.postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USER", Containers.postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", Containers.postgreSQLContainer::getPassword);
    }

    @Test
    public void contextLoads() {
        assertThat(tagRepository).isNotNull();
    }

    @Test
    public void getAllTags() {
        Iterable<Tag> allTags = tagRepository.findAll();
        assertThat(allTags).isNotNull();
        assertThat(allTags).isNotEmpty();
    }

    @Test
    public void findTagByName() {
        Tag tag0 = tagRepository.findByName("Tag0");
        assertThat(tag0).isNotNull();
        assertThat(tag0.getName()).isEqualTo("Tag0");

        Tag tag10 = tagRepository.findByName("Tag10");
        assertThat(tag10).isNull();
    }

    @Test
    public void findByTaggedObjectId() {
        List<Tag> tags = tagRepository.findByObjectId(1000L);
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(6);
    }

    @Test
    public void findByObjectClass() {
        List<Tag> projectTags = tagRepository.findByObjectClass("PROJECT");
        assertThat(projectTags).isNotNull();
        assertThat(projectTags).isNotEmpty();
        assertThat(projectTags.size()).isEqualTo(2);

        List<Tag> protocolTags = tagRepository.findByObjectClass("PROTOCOL");
        assertThat(protocolTags).isNotNull();
        assertThat(protocolTags).isNotEmpty();
        assertThat(protocolTags.size()).isEqualTo(2);

        List<Tag> experimentTags = tagRepository.findByObjectClass("EXPERIMENT");
        assertThat(experimentTags).isNotNull();
        assertThat(experimentTags).isNotEmpty();
        assertThat(experimentTags.size()).isEqualTo(2);

        List<Tag> featureTags = tagRepository.findByObjectClass("FEATURE");
        assertThat(featureTags).isNotNull();
        assertThat(featureTags).isNotEmpty();
        assertThat(featureTags.size()).isEqualTo(2);

        List<Tag> plateTags = tagRepository.findByObjectClass("PLATE");
        assertThat(plateTags).isNotNull();
        assertThat(plateTags).isNotEmpty();
        assertThat(plateTags.size()).isEqualTo(2);
    }

    @Test
    public void findByObjectIdAndObjectClass() {
        List<Tag> tags = tagRepository.findByObjectIdAndObjectClass(1000L, "PROJECT");
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(2);


        List<Tag> tags1 = tagRepository.findByObjectIdAndObjectClass(3000L, "WELL");
        assertThat(tags1).isNotNull();
        assertThat(tags1).isEmpty();
    }



}
