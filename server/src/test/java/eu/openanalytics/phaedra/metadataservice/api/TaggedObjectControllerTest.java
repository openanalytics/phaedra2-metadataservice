package eu.openanalytics.phaedra.metadataservice.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.support.Containers;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaggedObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllTaggedObjectsByObjectClass() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/tagged_objects/{objectClass}", ObjectClass.PROJECT))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TaggedObjectDTO> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(2);
    }

    @Test
    void getTaggedObjectByIdAndClass() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/tagged_objects/{objectClass}", ObjectClass.EXPERIMENT)
                        .param("objectId", String.valueOf(1000L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TaggedObjectDTO> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(1);
    }

    @Test
    void getTaggedObjectsByTag() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/tagged_objects/{objectClass}", ObjectClass.FEATURE)
                        .param("tag", "Tag6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TaggedObjectDTO> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(1);
    }
}
