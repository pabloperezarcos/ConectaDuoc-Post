package com.conectaduoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conectaduoc.model.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    // Métodos personalizados para buscar comentarios por 
    List<PostComment> findByIdPost(Long idPost);
    // Método para buscar comentarios por idUser
    List<PostComment> findByIdUser(Long idUser);
}

