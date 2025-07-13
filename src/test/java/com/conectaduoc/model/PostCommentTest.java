package com.conectaduoc.model;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PostCommentTest {

    @Test
    void testGettersAndSetters() {
        PostComment c = new PostComment();
        c.setIdComment(1L);
        c.setIdUser(2L);
        c.setIdPost(3L);
        c.setContent("Buen post!");
        Date now = new Date();
        c.setCreatedDate(now);

        assertEquals(1L, c.getIdComment());
        assertEquals(2L, c.getIdUser());
        assertEquals(3L, c.getIdPost());
        assertEquals("Buen post!", c.getContent());
        assertEquals(now, c.getCreatedDate());
    }
}
