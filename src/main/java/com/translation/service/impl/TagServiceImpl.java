package com.translation.service.impl;

import com.translation.dto.request.TagRequest;
import com.translation.dto.response.TagResponse;
import com.translation.exception.ResourceNotFoundException;
import com.translation.mapper.TagMapper;
import com.translation.model.entity.Tag;
import com.translation.repository.TagRepository;
import com.translation.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = tagMapper.toEntity(tagRequest);
        tag = tagRepository.save(tag);
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional
    @CacheEvict(value = "tags", key = "#id")
    public TagResponse updateTag(Long id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        
        tag.setName(tagRequest.getName());
        tag = tagRepository.save(tag);
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "tags", key = "#id")
    public TagResponse getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allTags", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<TagResponse> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .map(tagMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> searchTags(String query) {
        return tagRepository.findByNameContainingIgnoreCase(query).stream()
                .map(tagMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"tags", "allTags"}, allEntries = true)
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }
}
