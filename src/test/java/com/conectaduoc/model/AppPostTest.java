package com.conectaduoc.model;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppPostTest {

    @Test
    void testGettersAndSetters() {
        AppPost post = new AppPost();
        post.setIdPost(1L);
        post.setIdUser(2L);
        post.setTitle("Título");
        post.setContent("Contenido");
        post.setIdCategory(3L);
        post.setViews(10);
        Date now = new Date();
        post.setCreatedDate(now);

        assertEquals(1L, post.getIdPost());
        assertEquals(2L, post.getIdUser());
        assertEquals("Título", post.getTitle());
        assertEquals("Contenido", post.getContent());
        assertEquals(3L, post.getIdCategory());
        assertEquals(10, post.getViews());
        assertEquals(now, post.getCreatedDate());
    }
}
