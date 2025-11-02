package com.translation.mapper;

import com.translation.dto.request.TagRequest;
import com.translation.dto.response.TagResponse;
import com.translation.model.entity.Tag;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;

@Mapper(componentModel = "spring")
public abstract class TagMapper {
    
    @Autowired
    protected org.springframework.context.ApplicationContext applicationContext;
    
    private static TagMapper instance;
    
    @PostConstruct
    private void registerInstance() {
        instance = this;
    }
    
    public static TagMapper getInstance() {
        return instance;
    }
    
    public Tag toEntity(TagRequest request) {
        if (request == null) {
            return null;
        }
        
        Tag tag = new Tag();
        tag.setName(request.getName());
        return tag;
    }
    
    public TagResponse toResponse(Tag tag) {
        if (tag == null) {
            return null;
        }
        
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setName(tag.getName());
        return response;
    }
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateEntity(@MappingTarget Tag tag, TagRequest request) {
        if (request == null) {
            return;
        }
        
        if (request.getName() != null) {
            tag.setName(request.getName());
        }
    }
    
    public Tag fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }
}
