package com.translation.service.impl;

import com.translation.dto.request.TranslationKeyRequest;
import com.translation.dto.response.TranslationKeyResponse;
import com.translation.exception.ResourceNotFoundException;
import com.translation.mapper.TranslationKeyMapper;
import com.translation.model.entity.Tag;
import com.translation.model.entity.TranslationKey;
import com.translation.repository.TagRepository;
import com.translation.repository.TranslationKeyRepository;
import com.translation.service.TranslationKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationKeyServiceImpl implements TranslationKeyService {

    private final TranslationKeyRepository translationKeyRepository;
    private final TagRepository tagRepository;
    private final TranslationKeyMapper translationKeyMapper;

    @Override
    @Transactional
    public TranslationKeyResponse createTranslationKey(TranslationKeyRequest keyRequest) {
        TranslationKey key = translationKeyMapper.toEntity(keyRequest);
        
        // Add tags if any
        if (keyRequest.getTagIds() != null && !keyRequest.getTagIds().isEmpty()) {
            Set<Tag> tags = tagRepository.findAllById(keyRequest.getTagIds()).stream()
                    .collect(Collectors.toSet());
            key.setTags(tags);
        }
        
        key = translationKeyRepository.save(key);
        return translationKeyMapper.toResponse(key);
    }

    @Override
    @Transactional
    @CacheEvict(value = "translationKeys", key = "#id")
    public TranslationKeyResponse updateTranslationKey(Long id, TranslationKeyRequest keyRequest) {
        TranslationKey key = translationKeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Translation key not found with id: " + id));
        
        key.setKeyName(keyRequest.getKeyName());
        key.setDescription(keyRequest.getDescription());
        
        // Update tags
        if (keyRequest.getTagIds() != null) {
            Set<Tag> tags = tagRepository.findAllById(keyRequest.getTagIds()).stream()
                    .collect(Collectors.toSet());
            key.setTags(tags);
        }
        
        key = translationKeyRepository.save(key);
        return translationKeyMapper.toResponse(key);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "translationKeys", key = "#id")
    public TranslationKeyResponse getTranslationKeyById(Long id) {
        TranslationKey key = translationKeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Translation key not found with id: " + id));
        return translationKeyMapper.toResponse(key);
    }

   

    @Override
    @Transactional(readOnly = true)
    public List<TranslationKeyResponse> searchTranslationKeys(String query) {
        return translationKeyRepository.findByKeyNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(translationKeyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TranslationKeyResponse> getTranslationKeysByTag(String tagName) {
        return translationKeyRepository.findByTagsName(tagName).stream()
                .map(translationKeyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"translationKeys", "allTranslationKeys"}, allEntries = true)
    public void deleteTranslationKey(Long id) {
        if (!translationKeyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Translation key not found with id: " + id);
        }
        translationKeyRepository.deleteById(id);
    }
}
