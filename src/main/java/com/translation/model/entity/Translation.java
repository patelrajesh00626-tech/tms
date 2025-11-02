package com.translation.model.entity;

import com.translation.model.enums.Locale;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "translation", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"key_id", "locale"}, name = "uk_translation_key_locale")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translation extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_id", nullable = false)
    private TranslationKey key;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10)")
    private Locale locale;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
   
}
