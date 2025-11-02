package com.translation.service;

import com.translation.dto.request.TagRequest;
import com.translation.dto.response.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    TagResponse createTag(TagRequest tagRequest);
    TagResponse updateTag(Long id, TagRequest tagRequest);
    TagResponse getTagById(Long id);
    Page<TagResponse> getAllTags(Pageable pageable);
    List<TagResponse> searchTags(String query);
    void deleteTag(Long id);
}
