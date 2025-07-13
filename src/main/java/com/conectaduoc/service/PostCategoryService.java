package com.conectaduoc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conectaduoc.model.PostCategory;
import com.conectaduoc.repository.PostCategoryRepository;

@Service
public class PostCategoryService {

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    public List<PostCategory> listPostCategory() {
        List<PostCategory> categorias = postCategoryRepository.findAll();
        return categorias;
    }

    public Optional<PostCategory> getPostCategory(Long id) {
        return postCategoryRepository.findById(id);
    }

    public PostCategory savPostCategory(PostCategory comentario) {
        return postCategoryRepository.save(comentario);
    }

    public void deletePostCategory(Long id) {
        postCategoryRepository.deleteById(id);
    }
}
