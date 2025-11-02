package com.translation.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

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
        tag.addTranslationKey(key1);
        
        assertEquals(1, tag.getTranslationKeys().size());
        assertTrue(tag.getTranslationKeys().contains(key1));
        assertTrue(key1.getTags().contains(tag));
    }

    @Test
    void testRemoveTranslationKey() {
        // Add and then remove a key
        tag.addTranslationKey(key1);
        tag.removeTranslationKey(key1);
        
        assertTrue(tag.getTranslationKeys().isEmpty());
        assertFalse(key1.getTags().contains(tag));
    }

    @Test
    void testAddMultipleTranslationKeys() {
        tag.addTranslationKey(key1);
        tag.addTranslationKey(key2);
        
        assertEquals(2, tag.getTranslationKeys().size());
        assertTrue(tag.getTranslationKeys().contains(key1));
        assertTrue(tag.getTranslationKeys().contains(key2));
        assertTrue(key1.getTags().contains(tag));
        assertTrue(key2.getTags().contains(tag));
    }

    @Test
    void testRemoveNonExistentKey() {
        // Try to remove a key that was never added
        tag.removeTranslationKey(key1);
        
        assertTrue(tag.getTranslationKeys().isEmpty());
    }

    @Test
    void testTagEquality() {
        Tag tag1 = Tag.builder().name("desktop").build();
        Tag tag2 = Tag.builder().name("desktop").build();
        
        // Test equals and hashCode
        assertEquals(tag1, tag2);
        assertEquals(tag1.hashCode(), tag2.hashCode());
        
        // Test with different name
        Tag tag3 = Tag.builder().name("mobile").build();
        assertNotEquals(tag1, tag3);
    }

    @Test
    void testToString() {
        assertNotNull(tag.toString());
        assertTrue(tag.toString().contains("name=mobile"));
    }
}
