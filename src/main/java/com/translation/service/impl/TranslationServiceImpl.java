package com.translation.service.impl;

import com.translation.dto.request.TranslationRequest;
import com.translation.dto.response.TranslationResponse;
import com.translation.exception.ResourceNotFoundException;
import com.translation.mapper.TranslationMapper;
import com.translation.model.entity.Translation;
import com.translation.model.entity.TranslationKey;
import com.translation.model.enums.Locale;
import com.translation.repository.TranslationKeyRepository;
import com.translation.repository.TranslationRepository;
import com.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationKeyRepository translationKeyRepository;
    private final TranslationMapper translationMapper;

    @Override
    @Transactional
    public TranslationResponse createTranslation(TranslationRequest translationRequest) {
        TranslationKey key = translationKeyRepository.findById(translationRequest.getKeyId())
                .orElseThrow(() -> new ResourceNotFoundException("Translation key not found with id: " + translationRequest.getKeyId()));
        
        // Check if translation for this locale already exists
        translationRepository.findByKeyIdAndLocale(translationRequest.getKeyId(), translationRequest.getLocale())
                .ifPresent(t -> {
                    throw new IllegalStateException("Translation for key " + key.getKeyName() + 
                            " and locale " + translationRequest.getLocale() + " already exists");
                });
        
        Translation translation = translationMapper.toEntity(translationRequest);
        translation.setKey(key);
        
        translation = translationRepository.save(translation);
        return translationMapper.toResponse(translation);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"translations", "translationExports"}, allEntries = true)
    public TranslationResponse updateTranslation(Long id, TranslationRequest translationRequest) {
        Translation translation = translationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Translation not found with id: " + id));
        
        // If locale is being changed, check for duplicates
        if (!translation.getLocale().equals(translationRequest.getLocale())) {
            translationRepository.findByKeyIdAndLocale(translation.getKey().getId(), translationRequest.getLocale())
                    .ifPresent(t -> {
                        if (!t.getId().equals(id)) {
                            throw new IllegalStateException("Translation for this key and locale already exists");
                        }
                    });
        }
        
        translation.setContent(translationRequest.getContent());
        translation.setLocale(translationRequest.getLocale());
        
        translation = translationRepository.save(translation);
        return translationMapper.toResponse(translation);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "translations", key = "#id")
    public TranslationResponse getTranslationById(Long id) {
        Translation translation = translationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Translation not found with id: " + id));
        return translationMapper.toResponse(translation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TranslationResponse> getAllTranslations(Pageable pageable) {
        return translationRepository.findAll(pageable)
                .map(translationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TranslationResponse> getTranslationsByKeyId(Long keyId) {
        return translationRepository.findAllByKeyId(keyId).stream()
                .map(translationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TranslationResponse> getTranslationsByKeyName(String keyName) {
        return translationRepository.findByKeyKeyName(keyName).stream()
                .map(translationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TranslationResponse> searchTranslations(String query) {
        return translationRepository.findByContentContainingIgnoreCase(query).stream()
                .map(translationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "translationExports", key = "'all'")
    public Map<String, Map<Locale, String>> exportTranslations() {
        // Use a pageable query to avoid loading all translations at once
        Pageable pageable = Pageable.unpaged();
        List<Translation> translations = translationRepository.findAll(pageable).getContent();
        
        Map<String, Map<Locale, String>> result = new HashMap<>();
        
        translations.forEach(translation -> {
            String key = translation.getKey().getKeyName();
            result.computeIfAbsent(key, k -> new EnumMap<>(Locale.class))
                  .put(translation.getLocale(), translation.getContent());
        });
        
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "translationExports", key = "#locale")
    public Map<String, String> exportTranslationsForLocale(Locale locale) {
        // Use a pageable query to avoid loading all translations at once
        Pageable pageable = Pageable.unpaged();
        return translationRepository.findByLocale(locale, pageable).stream()
                .collect(Collectors.toMap(
                        t -> t.getKey().getKeyName(),
                        Translation::getContent
                ));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"translations", "translationExports"}, allEntries = true)
    public void deleteTranslation(Long id) {
        if (!translationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Translation not found with id: " + id);
        }
        translationRepository.deleteById(id);
    }
}
