package com.translation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class TranslationKeyRequest {
    
    @NotBlank(message = "Key name is required")
    @Size(min = 2, max = 255, message = "Key name must be between 2 and 255 characters")
    private String keyName;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    private Set<Long> tagIds;
}
