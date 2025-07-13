package com.conectaduoc.service;

import com.conectaduoc.model.PostScore;
import com.conectaduoc.repository.PostScoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostScoreServiceTest {

    @Mock
    private PostScoreRepository repository;

    @InjectMocks
    private PostScoreService service;

    @Test
    void listScoresTest() {
        List<PostScore> scores = new ArrayList<>();
        PostScore score1 = new PostScore();
        scores.add(score1);
        PostScore score2 = new PostScore();
        scores.add(score2);

        when(repository.findAll()).thenReturn(scores);
        List<PostScore> result = service.listScores();
        assertEquals(2, result.size());
        assertEquals(score1, result.get(0));
        assertEquals(score2, result.get(1));
    }

    @Test
    void getScoreTest() {
        Long id = 1L;
        PostScore score = new PostScore();
        score.setIdScore(id);
        when(repository.findById(id)).thenReturn(Optional.of(score));
        Optional<PostScore> foundScore = service.getScore(id);
        assertTrue(foundScore.isPresent());
        assertEquals(score, foundScore.get());
    }

    @Test
    void saveScoreTest() {
        PostScore score = new PostScore();
        when(repository.save(score)).thenReturn(score);
        PostScore savedScore = service.saveOrUpdateScore(score);
        assertEquals(score, savedScore);
    }

    @Test
    void deleteScoreTest() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);
        service.deleteScore(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void findByIdPostTest() {
        Long idPost = 1L;
        List<PostScore> scores = new ArrayList<>();
        PostScore score1 = new PostScore();
        score1.setIdPost(idPost);
        scores.add(score1);
        PostScore score2 = new PostScore();
        score2.setIdPost(idPost);
        scores.add(score2);

        when(repository.findByIdPost(idPost)).thenReturn(scores);
        List<PostScore> result = service.findByIdPost(idPost);
        assertEquals(2, result.size());
        assertEquals(score1, result.get(0));
        assertEquals(score2, result.get(1));
    }

    @Test
    void findByIdUserTest() {
        Long idUser = 1L;
        List<PostScore> scores = new ArrayList<>();
        PostScore score1 = new PostScore();
        score1.setIdUser(idUser);
        scores.add(score1);
        PostScore score2 = new PostScore();
        score2.setIdUser(idUser);
        scores.add(score2);

        when(repository.findByIdUser(idUser)).thenReturn(scores);
        List<PostScore> result = service.findByIdUser(idUser);
        assertEquals(2, result.size());
        assertEquals(score1, result.get(0));
        assertEquals(score2, result.get(1));
    }

    @Test
    void findByIdUserAndIdPostTest() {
        Long idUser = 1L;
        Long idPost = 2L;
        PostScore score = new PostScore();
        score.setIdUser(idUser);
        score.setIdPost(idPost);

        when(repository.findByIdUserAndIdPost(idUser, idPost)).thenReturn(score);
        PostScore foundScore = service.findByIdUserAndIdPost(idUser, idPost);
        assertEquals(score, foundScore);
    }
}
