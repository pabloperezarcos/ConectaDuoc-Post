package com.conectaduoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.conectaduoc.model.PostScore;

public interface PostScoreRepository extends JpaRepository<PostScore, Long> {
    List<PostScore> findByIdPost(Long idPost);
    List<PostScore> findByIdUser(Long idUser);
    @Query("SELECT AVG(s.score) FROM PostScore s WHERE s.idPost = :idPost")
    Double getAverageScore(@Param("idPost") Long idPost);
    PostScore findByIdUserAndIdPost(Long idUser, Long idPost);
}
