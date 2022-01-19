package eu.openanalytics.phaedra.metadataservice.api;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openanalytics.phaedra.metadataservice.dto.TaggedObjectDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import eu.openanalytics.phaedra.metadataservice.support.Containers;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", Containers.postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USER", Containers.postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", Containers.postgreSQLContainer::getPassword);
    }

    @Test
    void addTag() throws Exception {
        TaggedObjectDTO taggedObject = new TaggedObjectDTO(1000L, "PROTOCOL", "protTag1");

        String requestBody = objectMapper.writeValueAsString(taggedObject);
        this.mockMvc.perform(post("/tag").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        MvcResult mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectId", valueOf(taggedObject.getObjectId()))
                        .param("objectClass", taggedObject.getObjectClass()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Tag> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(2);
    }

    @Test
    void removeTag() throws Exception{
        TaggedObjectDTO taggedObject1 = new TaggedObjectDTO(1000L, "PROJECT", "Tag0");
        String requestBody = objectMapper.writeValueAsString(taggedObject1);
        this.mockMvc.perform(delete("/tag")
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectId", valueOf(taggedObject1.getObjectId()))
                        .param("objectClass", taggedObject1.getObjectClass()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Tag> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(1);

        TaggedObjectDTO taggedObject2 = new TaggedObjectDTO(1000L, "PROJECT", "Tag1");
        requestBody = objectMapper.writeValueAsString(taggedObject2);
        this.mockMvc.perform(delete("/tag")
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectId", valueOf(taggedObject2.getObjectId()))
                        .param("objectClass", taggedObject2.getObjectClass()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isEmpty();
    }

//    @Test Disabled while auth is tested.
    void getAllTags() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Tag> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(10);
    }

    @Test
    void getTagsByObjectId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectId", valueOf(2000L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Tag> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(4);
    }

    @Test
    void getTagsByObjectClass() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectClass", ObjectClass.FEATURE.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Tag> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(2);

        mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectClass", ObjectClass.WELL.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isEmpty();
    }

    @Test
    void getTagsByObjectIdAndObjectClass() throws Exception {
        TaggedObjectDTO taggedObject = new TaggedObjectDTO(2000L, "PLATE", "Tag9");
        MvcResult mvcResult = this.mockMvc.perform(get("/tags")
                        .param("objectId", valueOf(taggedObject.getObjectId()))
                        .param("objectClass", taggedObject.getObjectClass()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Tag> tags = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags.size()).isEqualTo(1);
    }
}
