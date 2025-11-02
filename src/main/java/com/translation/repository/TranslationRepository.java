package com.translation.repository;

import com.translation.model.entity.Translation;
import com.translation.model.entity.TranslationKey;
import com.translation.model.enums.Locale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    
    Optional<Translation> findByKeyAndLocale(TranslationKey key, Locale locale);
    
    @Query("SELECT t FROM Translation t WHERE t.key.id = :keyId AND t.locale = :locale")
    Optional<Translation> findByKeyIdAndLocale(@Param("keyId") Long keyId, 
                                             @Param("locale") Locale locale);
    
    @Query("SELECT t FROM Translation t WHERE t.key.id = :keyId")
    List<Translation> findAllByKeyId(@Param("keyId") Long keyId);
    
    @Query("SELECT t FROM Translation t WHERE t.locale = :locale")
    List<Translation> findAllByLocale(@Param("locale") Locale locale);
    
    @Query("SELECT t FROM Translation t " +
           "JOIN t.key k " +
           "WHERE k.keyName = :keyName AND t.locale = :locale")
    Optional<Translation> findByKeyNameAndLocale(
        @Param("keyName") String keyName, 
        @Param("locale") Locale locale
    );
    
    @Query("SELECT t FROM Translation t " +
           "JOIN t.key k " +
           "WHERE t.locale = :locale AND LOWER(t.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Translation> searchByLocaleAndContent(
        @Param("locale") Locale locale,
        @Param("query") String query
    );
    
    boolean existsByKeyAndLocale(TranslationKey key, Locale locale);
}
