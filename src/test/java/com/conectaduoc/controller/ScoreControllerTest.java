package com.conectaduoc.controller;

import com.conectaduoc.model.PostScore;
import com.conectaduoc.repository.PostScoreRepository;
import com.conectaduoc.service.PostScoreService;
import com.conectaduoc.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreControllerTest {

    @Mock
    private PostScoreService scoreService;

    @Mock
    private PostScoreRepository scoreRepository;

    @InjectMocks
    private PostScoreController controller;

    @Test
    void listScore_debeRetornarLista() {
        PostScore s1 = new PostScore();
        s1.setIdUser(1L); s1.setIdPost(1L); s1.setScore(4);
        PostScore s2 = new PostScore();
        s2.setIdUser(2L); s2.setIdPost(1L); s2.setScore(5);

        when(scoreService.listScores()).thenReturn(Arrays.asList(s1, s2));
        List<PostScore> resp = controller.listScores();

        assertEquals(2, resp.size());
    }

    @Test
    void crearScore_debeCrearYRetornar201() {
        PostScore nuevo = new PostScore();
        nuevo.setIdUser(1L);
        nuevo.setIdPost(1L);
        nuevo.setScore(4);

        when(scoreRepository.findByIdUserAndIdPost(1L, 1L)).thenReturn(null); // Simula que NO existe previo
        when(scoreRepository.save(any(PostScore.class))).thenReturn(nuevo);

        ResponseEntity<PostScore> resp = controller.saveScore(nuevo);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(nuevo, resp.getBody());
    }

    @Test
    void obtenerScore_debeRetornarSiExiste() {
        PostScore score = new PostScore();
        score.setIdUser(1L); score.setIdPost(1L);
        when(scoreService.getScore(1L)).thenReturn(Optional.of(score));

        ResponseEntity<PostScore> resp = controller.getScore(1L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void obtenerScore_lanzaExcepcionSiNoExiste() {
        when(scoreService.getScore(10L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> controller.getScore(10L));
    }

    @Test
    void eliminarScore_debeEliminarSiExiste() {
        doNothing().when(scoreService).deleteScore(2L);

        ResponseEntity<Void> resp = controller.deleteScore(2L);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());

        verify(scoreService).deleteScore(2L);
    }
}
