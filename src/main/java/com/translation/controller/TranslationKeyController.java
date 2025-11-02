package com.translation.controller;

import com.translation.dto.request.TranslationKeyRequest;
import com.translation.dto.response.ApiResponse;
import com.translation.dto.response.TranslationKeyResponse;
import com.translation.service.TranslationKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseController.API_VERSION + "/translation-keys")
@RequiredArgsConstructor
@Tag(name = "Translation Key Management", description = "APIs for managing translation keys")
public class TranslationKeyController extends BaseController {

    private final TranslationKeyService translationKeyService;

    @PostMapping
    @Operation(summary = "Create a new translation key")
    public ResponseEntity<ApiResponse<TranslationKeyResponse>> createTranslationKey(
            @Valid @RequestBody TranslationKeyRequest request) {
        TranslationKeyResponse response = translationKeyService.createTranslationKey(request);
        return createdResponse(response.getId(), ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a translation key by ID")
    public ResponseEntity<ApiResponse<TranslationKeyResponse>> getTranslationKeyById(
            @PathVariable Long id) {
        TranslationKeyResponse response = translationKeyService.getTranslationKeyById(id);
        return okResponse(ApiResponse.success(response));
    }

    @GetMapping("/search")
    @Operation(summary = "Search translation keys by name or description")
    public ResponseEntity<ApiResponse<List<TranslationKeyResponse>>> searchTranslationKeys(
            @RequestParam String query) {
        return okResponse(ApiResponse.success(translationKeyService.searchTranslationKeys(query)));
    }

    @GetMapping("/by-tag/{tagName}")
    @Operation(summary = "Get translation keys by tag name")
    public ResponseEntity<ApiResponse<List<TranslationKeyResponse>>> getTranslationKeysByTag(
            @PathVariable String tagName) {
        return okResponse(ApiResponse.success(translationKeyService.getTranslationKeysByTag(tagName)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a translation key")
    public ResponseEntity<ApiResponse<TranslationKeyResponse>> updateTranslationKey(
            @PathVariable Long id,
            @Valid @RequestBody TranslationKeyRequest request) {
        TranslationKeyResponse response = translationKeyService.updateTranslationKey(id, request);
        return okResponse(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a translation key")
    public ResponseEntity<Void> deleteTranslationKey(@PathVariable Long id) {
        translationKeyService.deleteTranslationKey(id);
        return noContentResponse();
    }
}
