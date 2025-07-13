package com.conectaduoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.conectaduoc.exception.ResourceNotFoundException;
import com.conectaduoc.model.AppPost;
import com.conectaduoc.service.AppPostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
@Validated
public class AppPostController {

    @Autowired
    private AppPostService postService;

    // Listar posts, opcionalmente por categoría
    @GetMapping
    public ResponseEntity<List<AppPost>> listPost(@RequestParam(required = false) Long idCategory) {
        List<AppPost> posts;
        if (idCategory != null) {
            posts = postService.findByCategory(idCategory);
        } else {
            posts = postService.listPosts();
        }
        return ResponseEntity.ok(posts);
    }

    // Crear un nuevo post con validación
    @PostMapping
    public ResponseEntity<AppPost> createPost(@Valid @RequestBody AppPost post) {
        AppPost nuevoPost = postService.savePost(post);
        return new ResponseEntity<>(nuevoPost, HttpStatus.CREATED); // Devolver 201 Created
    }

    // Obtener un post por su ID con manejo de excepción
    @GetMapping("/{idPost}")
    public ResponseEntity<AppPost> getPost(@PathVariable Long idPost) {
        AppPost post = postService.getPost(idPost)
                .orElseThrow(() -> new ResourceNotFoundException("El post con ID " + idPost + " no fue encontrado."));
        return ResponseEntity.ok(post);
    }

    // Eliminar un post por su ID con manejo de excepción
    @DeleteMapping("/{idPost}")
    public ResponseEntity<Void> deletePost(@PathVariable Long idPost) {
        // Verifica si el post existe, si no, lanza la excepción
        postService.getPost(idPost)
                .orElseThrow(() -> new ResourceNotFoundException("El post con ID " + idPost + " no fue encontrado."));

        // Elimina el post si existe
        postService.deletePost(idPost);

        return ResponseEntity.noContent().build(); // Devolver 204 No Content
    }

    // Listar posts por idUser
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<AppPost>> listPostsByUser(@PathVariable int idUser) {
        List<AppPost> posts = postService.findPostByIdUser(idUser);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron posts para el usuario con ID " + idUser);
        }
        return ResponseEntity.ok(posts);
    }

    /* ADICIONALES */
    @PutMapping("/{idPost}/view")
    public ResponseEntity<Void> sumarVisualizacion(@PathVariable Long idPost) {
        postService.sumarVisualizacion(idPost);
        return ResponseEntity.ok().build();
    }

}
