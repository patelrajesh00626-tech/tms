package com.translation.controller;

import com.translation.dto.request.TagRequest;
import com.translation.dto.response.ApiResponse;
import com.translation.dto.response.TagResponse;
import com.translation.service.TagService;
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
@RequestMapping(BaseController.API_VERSION + "/tags")
@RequiredArgsConstructor
@Tag(name = "Tag Management", description = "APIs for managing tags")
public class TagController extends BaseController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "Create a new tag")
    public ResponseEntity<ApiResponse<TagResponse>> createTag(@Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.createTag(request);
        return createdResponse(response.getId(), ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a tag by ID")
    public ResponseEntity<ApiResponse<TagResponse>> getTagById(@PathVariable Long id) {
        TagResponse response = tagService.getTagById(id);
        return okResponse(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "Get all tags with pagination")
    public ResponseEntity<ApiResponse<Page<TagResponse>>> getAllTags(Pageable pageable) {
        return okResponse(ApiResponse.success(tagService.getAllTags(pageable)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search tags by name")
    public ResponseEntity<ApiResponse<List<TagResponse>>> searchTags(
            @RequestParam String query) {
        return okResponse(ApiResponse.success(tagService.searchTags(query)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a tag")
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(
            @PathVariable Long id, 
            @Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.updateTag(id, request);
        return okResponse(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return noContentResponse();
    }
}
