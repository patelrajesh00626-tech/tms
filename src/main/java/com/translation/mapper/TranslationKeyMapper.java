package com.translation.mapper;

import com.translation.dto.request.TranslationKeyRequest;
import com.translation.dto.response.TranslationKeyResponse;
import com.translation.model.entity.TranslationKey;
import com.translation.model.entity.Tag;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TranslationKeyMapper {
    
    @Autowired
    protected org.springframework.context.ApplicationContext applicationContext;
    
    @Autowired
    private TagMapper tagMapper;
    
    private static TranslationKeyMapper instance;
    
    @PostConstruct
    private void registerInstance() {
        instance = this;
    }
    
    public static TranslationKeyMapper getInstance() {
        return instance;
    }
    
    public TranslationKey toEntity(TranslationKeyRequest request) {
        if (request == null) {
            return null;
        }
        
        TranslationKey translationKey = new TranslationKey();
        translationKey.setKeyName(request.getKeyName());
        translationKey.setDescription(request.getDescription());
        
        if (request.getTagIds() != null) {
            translationKey.setTags(
                request.getTagIds().stream()
                    .map(tagMapper::fromId)
                    .collect(Collectors.toSet())
            );
        }
        
        return translationKey;
    }
    
    public TranslationKeyResponse toResponse(TranslationKey translationKey) {
        if (translationKey == null) {
            return null;
        }
        
        TranslationKeyResponse response = new TranslationKeyResponse();
        response.setId(translationKey.getId());
        response.setKeyName(translationKey.getKeyName());
        response.setDescription(translationKey.getDescription());
        
        if (translationKey.getTags() != null) {
            response.setTags(
                translationKey.getTags().stream()
                    .map(tagMapper::toResponse)
                    .collect(Collectors.toSet())
            );
        }
        
        return response;
    }
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateEntity(@MappingTarget TranslationKey translationKey, TranslationKeyRequest request) {
        if (request == null) {
            return;
        }
        
        if (request.getKeyName() != null) {
            translationKey.setKeyName(request.getKeyName());
        }
        
        if (request.getDescription() != null) {
            translationKey.setDescription(request.getDescription());
        }
        
        if (request.getTagIds() != null) {
            translationKey.setTags(
                request.getTagIds().stream()
                    .map(tagMapper::fromId)
                    .collect(Collectors.toSet())
            );
        }
    }
    
    public List<TranslationKeyResponse> toResponseList(List<TranslationKey> translationKeys) {
        if (translationKeys == null) {
            return null;
        }
        return translationKeys.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
    
    public TranslationKey fromId(Long id) {
        if (id == null) {
            return null;
        }
        TranslationKey translationKey = new TranslationKey();
        translationKey.setId(id);
        return translationKey;
    }
    
    protected Set<Tag> mapTagIdsToTranslationKeys(Set<Long> tagIds) {
        if (tagIds == null) {
            return null;
        }
        return tagIds.stream()
            .map(tagMapper::fromId)
            .collect(Collectors.toSet());
    }
}
