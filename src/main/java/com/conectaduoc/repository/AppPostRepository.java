package com.conectaduoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conectaduoc.model.AppPost;

public interface AppPostRepository extends JpaRepository<AppPost, Long> {
    // Método para encontrar posts por idUser
    List<AppPost> findByIdUser(long idUser);

    // Método para encontrar posts por idCategory
    List<AppPost> findByIdCategory(Long idCategory);

}
