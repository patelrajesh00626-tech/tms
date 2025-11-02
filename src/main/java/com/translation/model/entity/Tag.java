package com.translation.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag extends BaseEntity {
    
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
    
    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private Set<TranslationKey> translationKeys = new HashSet<>();
    
   
}
