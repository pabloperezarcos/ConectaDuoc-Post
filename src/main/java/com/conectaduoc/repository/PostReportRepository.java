package com.conectaduoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conectaduoc.model.PostReport;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    // Método para encontrar reportes por idUser
    List<PostReport> findByIdUser(int idUser);
    // Método para encontrar reportes por idPost
    List<PostReport> findByIdPost(Long idPost);
    // Método para encontrar reportes por idComment
    List<PostReport> findByIdComment(int idComment);
}
