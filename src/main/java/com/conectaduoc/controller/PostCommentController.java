package com.conectaduoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.conectaduoc.exception.ResourceNotFoundException;
import com.conectaduoc.model.PostComment;
import com.conectaduoc.service.PostCommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comment")
@Validated
public class PostCommentController {

    @Autowired
    private PostCommentService comentarioService;

    // Listar todos los comentarios
    @GetMapping
    public ResponseEntity<List<PostComment>> listComment() {
        List<PostComment> comentarios = comentarioService.listComment();
        return ResponseEntity.ok(comentarios);
    }

    // Crear un nuevo comentario
    @PostMapping
    public ResponseEntity<PostComment> crearComentario(@Valid @RequestBody PostComment Comment) {
        PostComment nuevoComentario = comentarioService.savComment(Comment);
        return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED); // Devolver 201 Created
    }

    // Obtener un comentario por su ID con manejo de excepción
    @GetMapping("/{id}")
    public ResponseEntity<PostComment> obtenerComentario(@PathVariable Long id) {
        PostComment Comment = comentarioService.getComment(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Comment con ID " + id + " no fue encontrado."));
        return ResponseEntity.ok(Comment);
    }

    // Obtener comentarios por ID de post
    @GetMapping("/post/{idPost}")
    public ResponseEntity<List<PostComment>> obtenerComentariosPorPost(@PathVariable Long idPost) {
        List<PostComment> comentarios = comentarioService.getCommentsByPostId(idPost);
        return ResponseEntity.ok(comentarios);
    }

    // Eliminar un comentario por su ID con manejo de excepción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long id) {
        // Verifica si el Comment existe, si no, lanza la excepción
        comentarioService.getComment(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Comment con ID " + id + " no fue encontrado."));

        // Elimina el comentario si existe
        comentarioService.deleteComment(id);

        return ResponseEntity.noContent().build(); // Devolver 204 No Content
    }

    // Actualizar un comentario existente con manejo de excepción
    @PutMapping("/{id}")
    public ResponseEntity<PostComment> actualizarComentario(@PathVariable Long id, @Valid @RequestBody PostComment CommentDetails) {
        PostComment Comment = comentarioService.getComment(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Comment con ID " + id + " no fue encontrado."));

        Comment.setContent(CommentDetails.getContent());
        Comment.setIdUser(CommentDetails.getIdUser());
        Comment.setIdPost(CommentDetails.getIdPost());
        Comment.setCreatedDate(CommentDetails.getCreatedDate());
        // Guarda el comentario actualizado
        PostComment updatedComment = comentarioService.savComment(Comment);
        return ResponseEntity.ok(updatedComment);
    }
}
