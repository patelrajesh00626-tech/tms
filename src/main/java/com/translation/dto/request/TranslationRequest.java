package com.translation.dto.request;

import com.translation.model.enums.Locale;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TranslationRequest {
    
    @NotNull(message = "Key ID is required")
    private Long keyId;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotNull(message = "Locale is required")
    private Locale locale;
}
