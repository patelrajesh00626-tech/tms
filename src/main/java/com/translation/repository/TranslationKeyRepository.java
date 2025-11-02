package com.translation.repository;

import com.translation.model.entity.TranslationKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationKeyRepository extends JpaRepository<TranslationKey, Long> {
    
    Optional<TranslationKey> findByKeyName(String keyName);
    
    @Query("SELECT DISTINCT k FROM TranslationKey k " +
           "JOIN k.tags t " +
           "WHERE t.id IN :tagIds " +
           "GROUP BY k.id " +
           "HAVING COUNT(DISTINCT t.id) = :tagCount")
    List<TranslationKey> findByAllTags(@Param("tagIds") List<Long> tagIds, 
                                     @Param("tagCount") long tagCount);
    
    @Query("SELECT k FROM TranslationKey k WHERE LOWER(k.keyName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<TranslationKey> searchByKeyName(@Param("query") String query);
    
    @Query("SELECT DISTINCT k FROM TranslationKey k " +
           "JOIN k.translations t " +
           "WHERE LOWER(t.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<TranslationKey> findByTranslationContentContaining(@Param("query") String query);
    
    // Additional required methods
    List<TranslationKey> findByKeyNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyName, String description);
    
    List<TranslationKey> findByTagsName(String tagName);
    
    Page<TranslationKey> findByKeyNameContainingIgnoreCase(String keyName, Pageable pageable);
    @Query("SELECT DISTINCT k FROM TranslationKey k " +
           "JOIN k.translations t " +
           "WHERE LOWER(t.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<TranslationKey> searchByContent(@Param("query") String query);
    
    boolean existsByKeyName(String keyName);
}
