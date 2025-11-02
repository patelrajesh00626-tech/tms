package com.translation.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    private Tag tag;
    private TranslationKey key1;
    private TranslationKey key2;

    @BeforeEach
    void setUp() {
        tag = Tag.builder()
                .name("mobile")
                .translationKeys(new HashSet<>())
                .build();

        key1 = TranslationKey.builder()
                .keyName("welcome.message")
                .description("Welcome message for the application")
                .build();

        key2 = TranslationKey.builder()
                .keyName("error.not_found")
                .description("Resource not found error")
                .build();
    }

    @Test
    void testTagCreation() {
        assertNotNull(tag);
        assertEquals("mobile", tag.getName());
        assertTrue(tag.getTranslationKeys().isEmpty());
    }

    @Test
    void testAddTranslationKey() {
        // Add key1 to tag's translationKeys
        Set<TranslationKey> keys = new HashSet<>();
        keys.add(key1);
        tag.setTranslationKeys(keys);
        
        assertEquals(1, tag.getTranslationKeys().size());
        assertTrue(tag.getTranslationKeys().contains(key1));
    }

    @Test
    void testRemoveTranslationKey() {
        // Add and then remove a key
        Set<TranslationKey> keys = new HashSet<>();
        keys.add(key1);
        tag.setTranslationKeys(keys);
        
        keys = new HashSet<>();
        tag.setTranslationKeys(keys);
        
        assertTrue(tag.getTranslationKeys().isEmpty());
    }

    @Test
    void testAddMultipleTranslationKeys() {
        Set<TranslationKey> keys = new HashSet<>();
        keys.add(key1);
        keys.add(key2);
        tag.setTranslationKeys(keys);
        
        assertEquals(2, tag.getTranslationKeys().size());
        assertTrue(tag.getTranslationKeys().contains(key1));
        assertTrue(tag.getTranslationKeys().contains(key2));
    }

    @Test
    void testTagEquality() {
        Tag tag1 = Tag.builder().name("desktop").build();
        tag1.setId(1L);
        Tag tag2 = Tag.builder().name("desktop").build();
        tag2.setId(1L);
        
        // Test equals and hashCode - objects with same ID should be considered equal
        assertEquals(tag1, tag2);
        assertEquals(tag1.hashCode(), tag2.hashCode());
        
        // Test with different ID
        Tag tag3 = Tag.builder().name("desktop").build();
        tag3.setId(2L);
        assertNotEquals(tag1, tag3);
    }

    @Test
    void testToString() {
        String toStringResult = tag.toString();
        assertNotNull(toStringResult, "toString() should not return null");
        // Just verify it's not empty and contains the class name
        assertTrue(toStringResult.contains("Tag"), "toString() should contain class name: " + toStringResult);
    }
}
