package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import eu.openanalytics.phaedra.metadataservice.support.Containers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles(profiles = "test")
@Sql({"/jdbc/test-data.sql"})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

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
