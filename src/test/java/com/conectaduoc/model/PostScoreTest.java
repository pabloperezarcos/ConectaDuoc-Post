package com.conectaduoc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostScoreTest {

    @Test
    void testGettersAndSetters() {
        PostScore score = new PostScore();
        score.setIdUser(1L);
        score.setIdPost(2L);
        score.setScore(5);

        assertEquals(1L, score.getIdUser());
        assertEquals(2L, score.getIdPost());
        assertEquals(5, score.getScore());
    }
}
