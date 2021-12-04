package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import eu.openanalytics.phaedra.metadataservice.support.Containers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaggedObjectRepositoryTest {

    @Autowired
    private TaggedObjectRepository taggedObjectRepository;

    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13-alpine")
            .withDatabaseName("phaedra2")
            .withUrlParam("currentSchema","metadata")
            .withPassword("inmemory")
            .withUsername("inmemory");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", Containers.postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USER", Containers.postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", Containers.postgreSQLContainer::getPassword);
    }

    @Test
    public void contextLoads() {
        assertThat(taggedObjectRepository).isNotNull();
    }

    @Test
    void findTaggedObjectByObjectIdAndObjectClass() {
        Long objectId = 2000L;
        ObjectClass objectClass = ObjectClass.PLATE;

        List<TaggedObject> result = taggedObjectRepository.findTaggedObjectByObjectIdAndObjectClass(objectId, objectClass);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).size().isEqualTo(1);
    }

    @Test
    void findAllByObjectClass() {
        ObjectClass objectClass = ObjectClass.FEATURE;

        List<TaggedObject> result = taggedObjectRepository.findAllByObjectClass(objectClass);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).size().isEqualTo(2);
    }
}
