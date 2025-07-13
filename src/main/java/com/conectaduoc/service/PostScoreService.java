package com.conectaduoc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conectaduoc.model.PostScore;
import com.conectaduoc.model.AppPost;
import com.conectaduoc.repository.PostScoreRepository;
import com.conectaduoc.repository.AppPostRepository;
import com.conectaduoc.dto.ScoreResumenDTO;

@Service
public class PostScoreService {

    @Autowired
    private PostScoreRepository postScoreRepository;

    @Autowired
    private AppPostRepository postRepository;

    // Listar todos los puntajes
    public List<PostScore> listScores() {
        return postScoreRepository.findAll();
    }

    // Buscar por ID
    public Optional<PostScore> getScore(Long id) {
        return postScoreRepository.findById(id);
    }

    // Guardar o actualizar (upsert por idUser e idPost)
    public PostScore saveOrUpdateScore(PostScore score) {
        PostScore existente = postScoreRepository.findByIdUserAndIdPost(score.getIdUser(), score.getIdPost());
        if (existente != null) {
            existente.setScore(score.getScore());
            return postScoreRepository.save(existente);
        } else {
            return postScoreRepository.save(score);
        }
    }

    // Borrar por ID
    public void deleteScore(Long id) {
        postScoreRepository.deleteById(id);
    }

    // Buscar por idPost
    public List<PostScore> findByIdPost(Long idPost) {
        return postScoreRepository.findByIdPost(idPost);
    }

    // Buscar por idUser
    public List<PostScore> findByIdUser(Long idUser) {
        return postScoreRepository.findByIdUser(idUser);
    }

    // Buscar score por idUser e idPost
    public PostScore findByIdUserAndIdPost(Long idUser, Long idPost) {
        return postScoreRepository.findByIdUserAndIdPost(idUser, idPost);
    }

    // Obtener resumen de scores para un usuario y opcionalmente por categoría
    public List<ScoreResumenDTO> getResumenDeScores(Long idUser, Long idCategoria) {
        List<AppPost> posts = (idCategoria != null)
                ? postRepository.findByIdCategory(idCategoria)
                : postRepository.findAll();

        List<ScoreResumenDTO> resumenes = new ArrayList<>();

        for (AppPost post : posts) {
            Long idPost = post.getIdPost();
            Double promedio = Optional.ofNullable(
                    postScoreRepository.getAverageScore(idPost)).orElse(0.0);

            PostScore miScore = postScoreRepository.findByIdUserAndIdPost(idUser, idPost);
            Integer scoreValue = (miScore == null) ? null : miScore.getScore();

            resumenes.add(new ScoreResumenDTO(idPost, promedio, scoreValue));
        }
        return resumenes;
    }

    // Promedio para un post
    public Double getAverageScore(Long idPost) {
        return Optional.ofNullable(postScoreRepository.getAverageScore(idPost)).orElse(0.0);
    }
}
