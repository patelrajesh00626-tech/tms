package com.translation.service;

import com.translation.dto.request.TranslationKeyRequest;
import com.translation.dto.response.TranslationKeyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TranslationKeyService {
    TranslationKeyResponse createTranslationKey(TranslationKeyRequest keyRequest);
    TranslationKeyResponse updateTranslationKey(Long id, TranslationKeyRequest keyRequest);
    TranslationKeyResponse getTranslationKeyById(Long id);
    List<TranslationKeyResponse> searchTranslationKeys(String query);
    void deleteTranslationKey(Long id);
    List<TranslationKeyResponse> getTranslationKeysByTag(String tagName);
}
