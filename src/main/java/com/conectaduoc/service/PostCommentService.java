package com.conectaduoc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conectaduoc.model.PostComment;
import com.conectaduoc.repository.PostCommentRepository;

@Service
public class PostCommentService {

    @Autowired
    private PostCommentRepository commentRepository;

    public List<PostComment> listComment() {
        return commentRepository.findAll();
    }

    public Optional<PostComment> getComment(Long id) {
        return commentRepository.findById(id);
    }

    public PostComment savComment(PostComment comentario) {
        return commentRepository.save(comentario);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<PostComment> getCommentsByPostId(Long idPost) {
        return commentRepository.findByIdPost(idPost);
    }
}

