package com.translation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslationKeyResponse {
    private Long id;
    private String keyName;
    private String description;
    private Set<TagResponse> tags;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
