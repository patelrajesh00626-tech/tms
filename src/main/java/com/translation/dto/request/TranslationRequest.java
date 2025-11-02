package com.translation.dto.request;

import com.translation.model.enums.Locale;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TranslationRequest {
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotNull(message = "Locale is required")
    private Locale locale;
}
