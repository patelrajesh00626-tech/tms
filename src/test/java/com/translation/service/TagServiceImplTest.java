package com.translation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.translation.dto.request.TagRequest;
import com.translation.dto.response.TagResponse;
import com.translation.exception.ResourceNotFoundException;
import com.translation.mapper.TagMapper;
import com.translation.model.entity.Tag;
import com.translation.repository.TagRepository;
import com.translation.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag tag;
    private TagRequest tagRequest;
    private TagResponse tagResponse;

    @BeforeEach
    void setUp() {
        tag = Tag.builder()
                .name("test-tag")
                .build();
        tag.setId(1L);

        tagRequest = new TagRequest();
        tagRequest.setName("test-tag");

        tagResponse = new TagResponse();
        tagResponse.setId(1L);
        tagResponse.setName("test-tag");
    }

    @Test
    void createTag_ShouldReturnCreatedTag() {
        // Arrange
        when(tagMapper.toEntity(any(TagRequest.class))).thenReturn(tag);
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        when(tagMapper.toResponse(any(Tag.class))).thenReturn(tagResponse);

        // Act
        TagResponse result = tagService.createTag(tagRequest);

        // Assert
        assertNotNull(result);
        assertEquals(tagResponse.getId(), result.getId());
        assertEquals(tagResponse.getName(), result.getName());
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void getTagById_ShouldReturnTag_WhenTagExists() {
        // Arrange
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagMapper.toResponse(any(Tag.class))).thenReturn(tagResponse);

        // Act
        TagResponse result = tagService.getTagById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(tagResponse.getId(), result.getId());
    }

    @Test
    void getTagById_ShouldThrowException_WhenTagNotFound() {
        // Arrange
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> tagService.getTagById(1L));
    }

    @Test
    void getAllTags_ShouldReturnPageOfTags() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Tag> tagPage = new PageImpl<>(List.of(tag));
        
        when(tagRepository.findAll(pageable)).thenReturn(tagPage);
        when(tagMapper.toResponse(any(Tag.class))).thenReturn(tagResponse);

        // Act
        Page<TagResponse> result = tagService.getAllTags(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(tagRepository, times(1)).findAll(pageable);
    }

    @Test
    void updateTag_ShouldUpdateExistingTag() {
        // Arrange
        Tag updatedTag = Tag.builder().name("updated-tag").build();
        updatedTag.setId(1L);
        TagResponse updatedResponse = new TagResponse();
        updatedResponse.setId(1L);
        updatedResponse.setName("updated-tag");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(updatedTag);
        when(tagMapper.toResponse(any(Tag.class))).thenReturn(updatedResponse);

        TagRequest updateRequest = new TagRequest();
        updateRequest.setName("updated-tag");

        // Act
        TagResponse result = tagService.updateTag(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("updated-tag", result.getName());
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void deleteTag_ShouldDeleteTag() {
        // Arrange
        when(tagRepository.existsById(1L)).thenReturn(true);

        // Act
        tagService.deleteTag(1L);

        // Assert
        verify(tagRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTag_ShouldThrowException_WhenTagNotFound() {
        // Arrange
        when(tagRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> tagService.deleteTag(1L));
        verify(tagRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchTags_ShouldReturnMatchingTags() {
        // Arrange
        List<Tag> tags = Arrays.asList(tag);
        when(tagRepository.findByNameContainingIgnoreCase("test")).thenReturn(tags);
        when(tagMapper.toResponse(any(Tag.class))).thenReturn(tagResponse);

        // Act
        List<TagResponse> results = tagService.searchTags("test");

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        verify(tagRepository, times(1)).findByNameContainingIgnoreCase("test");
    }
}
