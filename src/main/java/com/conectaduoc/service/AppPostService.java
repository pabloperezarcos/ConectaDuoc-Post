package com.conectaduoc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conectaduoc.exception.ResourceNotFoundException;
import com.conectaduoc.model.AppPost;
import com.conectaduoc.repository.AppPostRepository;

import jakarta.transaction.Transactional;

@Service
public class AppPostService {

    @Autowired
    private AppPostRepository postRepository;

    public List<AppPost> listPosts() {
        return postRepository.findAll();
    }

    public Optional<AppPost> getPost(Long idUsuario) {
        return postRepository.findById(idUsuario);
    }

    public AppPost savePost(AppPost post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<AppPost> findPostByIdUser(int idUser) {
        return postRepository.findByIdUser(idUser);
    }

    public List<AppPost> findByCategory(Long idCategory) {
        return postRepository.findByIdCategory(idCategory);
    }

    /* ADICIONALES */
    @Transactional
    public void sumarVisualizacion(Long idPost) {
        AppPost post = postRepository.findById(idPost)
                .orElseThrow(() -> new ResourceNotFoundException("El post no existe."));
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
    }

}
