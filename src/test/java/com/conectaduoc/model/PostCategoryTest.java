package com.conectaduoc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostCategoryTest {

    @Test
    void testGettersAndSetters() {
        PostCategory cat = new PostCategory();
        cat.setIdCategory(1L);
        cat.setName("Noticias");
        cat.setDescription("Solo noticias");
        cat.setStatus(1);

        assertEquals(1L, cat.getIdCategory());
        assertEquals("Noticias", cat.getName());
        assertEquals("Solo noticias", cat.getDescription());
        assertEquals(1, cat.getStatus());
    }
}
