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
package eu.openanalytics.phaedra.metadataservice.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.support.Containers;

@Testcontainers
@SpringBootTest
@Sql({"/jdbc/test-data.sql"})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PropertyControllerTest {
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
    public void createProperty() throws Exception {
        PropertyDTO property = new PropertyDTO("ProtocolProperty1", "Newly created protocol", 1001L, "PROTOCOL");

        String requestBody = objectMapper.writeValueAsString(property);
        MvcResult mvcResult = this.mockMvc.perform(post("/properties").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        PropertyDTO propertyDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PropertyDTO.class);
        assertThat(propertyDTO).isNotNull();
        assertThat(propertyDTO.getObjectId()).isEqualTo(1001L);
        assertThat(propertyDTO.getObjectClass()).isEqualTo("PROTOCOL");
    }

    @Test
    public void deleteProtocol() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO("NumberOfExperiments", 1000L, "PROJECT");

        MvcResult mvcResult = this.mockMvc.perform(delete("/properties")
                        .param("propertyName", propertyDTO.getPropertyName())
                        .param("objectId", String.valueOf(propertyDTO.getObjectId()))
                        .param("objectClass", propertyDTO.getObjectClass()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        PropertyDTO deletedPropertyDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PropertyDTO.class);
        assertThat(deletedPropertyDTO).isNotNull();
        assertThat(deletedPropertyDTO.getPropertyName()).isEqualTo(propertyDTO.getPropertyName());
        assertThat(deletedPropertyDTO.getObjectId()).isEqualTo(propertyDTO.getObjectId());
        assertThat(deletedPropertyDTO.getObjectClass()).isEqualTo(propertyDTO.getObjectClass());
    }

    @Test
    public void updateProperty() throws Exception {
        String propertyName = "NumberOfFeatures";
        Long objectId = 2000L;
        String objectClass = "PROTOCOL";

        MvcResult mvcResult = this.mockMvc.perform(get("/properties")
                        .param("propertyName", propertyName)
                        .param("objectId", String.valueOf(objectId))
                        .param("objectClass", objectClass))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<PropertyDTO> results = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        
        PropertyDTO existingPropertyDTO = results.get(0);
        assertThat(existingPropertyDTO).isNotNull();
        assertThat(existingPropertyDTO.getPropertyName()).isEqualTo(propertyName);
        assertThat(existingPropertyDTO.getObjectId()).isEqualTo(objectId);
        assertThat(existingPropertyDTO.getObjectClass()).isEqualTo(objectClass);

        existingPropertyDTO.setPropertyValue("15");

        String requestBody = objectMapper.writeValueAsString(existingPropertyDTO);
        mvcResult = this.mockMvc.perform(put("/properties").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        PropertyDTO updatedPropertyDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PropertyDTO.class);
        assertThat(existingPropertyDTO).isNotNull();
        assertThat(updatedPropertyDTO.getPropertyValue()).isEqualTo(existingPropertyDTO.getPropertyValue());
        assertThat(existingPropertyDTO.getPropertyName()).isEqualTo(existingPropertyDTO.getPropertyName());
        assertThat(existingPropertyDTO.getObjectId()).isEqualTo(existingPropertyDTO.getObjectId());
        assertThat(existingPropertyDTO.getObjectClass()).isEqualTo(existingPropertyDTO.getObjectClass());
    }

    @Test
    public void getProperties() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/properties"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<PropertyDTO> results = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
    }
}
