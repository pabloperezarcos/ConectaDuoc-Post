package com.conectaduoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.conectaduoc.exception.ResourceNotFoundException;
import com.conectaduoc.model.PostScore;
import com.conectaduoc.model.AppPost;
import com.conectaduoc.service.PostScoreService;
import com.conectaduoc.dto.ScoreResumenDTO;
import com.conectaduoc.repository.PostScoreRepository;
import com.conectaduoc.repository.AppPostRepository;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/score")
@Validated
public class PostScoreController {

    @Autowired
    private PostScoreService scoreService;
    @Autowired
    private PostScoreRepository scoreRepository;
    @Autowired
    private AppPostRepository postRepository;

    @GetMapping("/list")
    public List<PostScore> listScores() {
        return scoreService.listScores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostScore> getScore(@PathVariable Long id) {
        PostScore score = scoreService.getScore(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found with id: " + id));
        return ResponseEntity.ok(score);
    }

    @PostMapping("/save")
    public ResponseEntity<PostScore> saveScore(@Valid @RequestBody PostScore score) {
        PostScore existente = scoreRepository.findByIdUserAndIdPost(score.getIdUser(), score.getIdPost());

        PostScore resultado;
        if (existente != null) {
            // ya existe → actualizar
            existente.setScore(score.getScore());
            resultado = scoreRepository.save(existente);
        } else {
            // no existe → guardar nuevo
            resultado = scoreRepository.save(score);
        }

        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{idPost}")
    public List<PostScore> findByIdPost(@PathVariable Long idPost) {
        return scoreService.findByIdPost(idPost);
    }

    @GetMapping("/user/{idUser}")
    public List<PostScore> findByIdUser(@PathVariable Long idUser) {
        return scoreService.findByIdUser(idUser);
    }

    @GetMapping("/user/{idUser}/post/{idPost}")
    public ResponseEntity<PostScore> findByIdUserAndIdPost(@PathVariable Long idUser, @PathVariable Long idPost) {
        PostScore score = scoreService.findByIdUserAndIdPost(idUser, idPost);
        if (score == null) {
            throw new ResourceNotFoundException("Score not found for user: " + idUser + " and post: " + idPost);
        }
        return ResponseEntity.ok(score);
    }

    /* MÉTODOS ADICIONALES */
    @GetMapping("/resumen")
    public ResponseEntity<?> getResumenDeScores(@RequestParam Long idUser,
            @RequestParam(required = false) Long idCategoria) {
        try {
            List<AppPost> posts = (idCategoria != null)
                    ? postRepository.findByIdCategory(idCategoria)
                    : postRepository.findAll();

            List<ScoreResumenDTO> resumenes = new ArrayList<>();

            for (AppPost post : posts) {
                Long idPost = post.getIdPost();
                Double promedio = Optional.ofNullable(
                        scoreRepository.getAverageScore(idPost)).orElse(0.0);

                PostScore miScore = scoreRepository.findByIdUserAndIdPost(idUser, idPost);
                Integer scoreValue = (miScore == null) ? null : miScore.getScore();

                resumenes.add(new ScoreResumenDTO(idPost, promedio, scoreValue));
            }
            return ResponseEntity.ok(resumenes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno en el servidor: " + e.getMessage());
        }
    }

    @GetMapping("/average/{idPost}")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long idPost) {
        Double avg = Optional.ofNullable(scoreRepository.getAverageScore(idPost)).orElse(0.0);
        return ResponseEntity.ok(avg);
    }

}