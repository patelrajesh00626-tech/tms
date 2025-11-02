package com.translation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.translation.dto.request.TagRequest;
import com.translation.model.entity.Tag;
import com.translation.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.translation.BaseIntegrationTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class TagControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
    }

    @Test
    void createTag_ShouldReturnCreatedTag() throws Exception {
        // Given
        TagRequest request = new TagRequest();
        request.setName("test-tag");

        // When & Then
        mockMvc.perform(post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.name", is("test-tag")))
                .andExpect(jsonPath("$.data.id", notNullValue()));
    }

    @Test
    void getTagById_ShouldReturnTag() throws Exception {
        // Given
        Tag tag = new Tag();
        tag.setName("test-tag");
        Tag savedTag = tagRepository.save(tag);

        // When & Then
        mockMvc.perform(get("/api/v1/tags/" + savedTag.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(savedTag.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is("test-tag")));
    }

    @Test
    void getTagById_ShouldReturnNotFound_WhenTagDoesNotExist() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/tags/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void getAllTags_ShouldReturnPaginatedTags() throws Exception {
        // Given
        for (int i = 1; i <= 3; i++) {
            Tag tag = new Tag();
            tag.setName("tag-" + i);
            tagRepository.save(tag);
        }

        // When & Then
        mockMvc.perform(get("/api/v1/tags?page=0&size=2&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.content", hasSize(2)))
                .andExpect(jsonPath("$.data.totalElements", is(3)));
    }

    @Test
    void updateTag_ShouldUpdateExistingTag() throws Exception {
        // Given
        Tag tag = new Tag();
        tag.setName("old-name");
        Tag savedTag = tagRepository.save(tag);

        TagRequest updateRequest = new TagRequest();
        updateRequest.setName("new-name");

        // When & Then
        mockMvc.perform(put("/api/v1/tags/" + savedTag.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.name", is("new-name")));
    }

    @Test
    void deleteTag_ShouldDeleteTag() throws Exception {
        // Given
        Tag tag = new Tag();
        tag.setName("to-delete");
        Tag savedTag = tagRepository.save(tag);

        // When & Then
        mockMvc.perform(delete("/api/v1/tags/" + savedTag.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/api/v1/tags/" + savedTag.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchTags_ShouldReturnMatchingTags() throws Exception {
        // Given
        Tag tag1 = new Tag();
        tag1.setName("backend-tag");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("frontend-tag");
        tagRepository.save(tag2);

        // When & Then
        mockMvc.perform(get("/api/v1/tags/search?query=front"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("frontend-tag")));
    }
}
