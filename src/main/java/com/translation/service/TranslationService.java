package com.translation.service;

import com.translation.dto.request.TranslationRequest;
import com.translation.dto.response.TranslationResponse;
import com.translation.model.enums.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TranslationService {
    TranslationResponse createTranslation(TranslationRequest translationRequest);
    TranslationResponse updateTranslation(Long id, TranslationRequest translationRequest);
    TranslationResponse getTranslationById(Long id);
    Page<TranslationResponse> getAllTranslations(Pageable pageable);
    List<TranslationResponse> getTranslationsByKeyId(Long keyId);
    List<TranslationResponse> getTranslationsByKeyName(String keyName);
    List<TranslationResponse> searchTranslations(String query);
    Map<String, Map<Locale, String>> exportTranslations();
    Map<String, String> exportTranslationsForLocale(Locale locale);
    void deleteTranslation(Long id);
}
