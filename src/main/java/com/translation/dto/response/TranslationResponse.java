package com.translation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.translation.model.enums.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslationResponse {
    private Long id;
    private String content;
    private Locale locale;
    private String keyName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
