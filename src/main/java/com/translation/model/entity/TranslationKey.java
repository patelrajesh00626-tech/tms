package com.translation.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translation_key")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranslationKey extends BaseEntity {
    
    @Column(name = "key_name", nullable = false, unique = true)
    private String keyName;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "key", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Translation> translations = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "translation_key_tags",
        joinColumns = @JoinColumn(name = "translation_key_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();
    
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getTranslationKeys().add(this);
    }
    
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getTranslationKeys().remove(this);
    }
    
    public void addTranslation(Translation translation) {
        this.translations.add(translation);
        translation.setKey(this);
    }
    
    public void removeTranslation(Translation translation) {
        this.translations.remove(translation);
        translation.setKey(null);
    }
   
}
