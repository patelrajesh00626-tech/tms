package com.translation.mapper;

import com.translation.dto.request.TranslationRequest;
import com.translation.dto.response.TranslationResponse;
import com.translation.model.entity.Translation;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class TranslationMapper {
    
    @Autowired
    protected org.springframework.context.ApplicationContext applicationContext;
    
    private static TranslationMapper instance;
    
    @PostConstruct
    private void registerInstance() {
        instance = this;
    }
    
    public static TranslationMapper getInstance() {
        return instance;
    }
    
    public Translation toEntity(TranslationRequest request) {
        if (request == null) {
            return null;
        }
        
        Translation translation = new Translation();
        translation.setLocale(request.getLocale());
        translation.setContent(request.getContent());
        return translation;
    }
    
    public TranslationResponse toResponse(Translation translation) {
        if (translation == null) {
            return null;
        }
        
        TranslationResponse response = new TranslationResponse();
        response.setId(translation.getId());
        response.setLocale(translation.getLocale());
        response.setContent(translation.getContent());
        return response;
    }
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateEntity(@MappingTarget Translation translation, TranslationRequest request) {
        if (request == null) {
            return;
        }
        
        if (request.getLocale() != null) {
            translation.setLocale(request.getLocale());
        }
if (request.getContent() != null) {
            translation.setContent(request.getContent());
        }
    }
    
    public Translation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Translation translation = new Translation();
        translation.setId(id);
        return translation;
    }
}
