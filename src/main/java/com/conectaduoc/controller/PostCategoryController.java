package com.conectaduoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conectaduoc.exception.ResourceNotFoundException;
import com.conectaduoc.model.PostCategory;
import com.conectaduoc.service.PostCategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post-category")
@Validated
public class PostCategoryController {

    @Autowired
    private PostCategoryService postCategoryService;

    // Listar todas las categorías de publicaciones
    @GetMapping
    public ResponseEntity<List<PostCategory>> listPostCategory() {
        List<PostCategory> postCategories = postCategoryService.listPostCategory();
        // System.out.println("CATEGORÍAS LEÍDAS: " + postCategories.size());
        return ResponseEntity.ok(postCategories);
    }

    // Crear una nueva categoría de publicación
    @PostMapping
    public ResponseEntity<PostCategory> createPostCategory(@Valid @RequestBody PostCategory postCategory) {
        PostCategory nuevoPostCategory = postCategoryService.savPostCategory(postCategory);
        return new ResponseEntity<>(nuevoPostCategory, HttpStatus.CREATED); // Devolver 201 Created
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCategory> obtenerPostCategory(@PathVariable Long id) {
        PostCategory postCategory = postCategoryService.getPostCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoría de publicación con ID " + id + " no fue encontrada."));
        return ResponseEntity.ok(postCategory);
    }

    // Eliminar una categoría de publicación por su ID con manejo de excepción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPostCategory(@PathVariable Long id) {
        postCategoryService.getPostCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoría de publicación con ID " + id + " no fue encontrada."));

        postCategoryService.deletePostCategory(id);

        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Actualizar una categoría de publicación existente con manejo de excepción
    @PutMapping("/{id}")
    public ResponseEntity<PostCategory> actualizarPostCategory(@PathVariable Long id,
            @Valid @RequestBody PostCategory postCategoryDetails) {
        PostCategory postCategory = postCategoryService.getPostCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoría de publicación con ID " + id + " no fue encontrada."));

        postCategory.setName(postCategoryDetails.getName());
        postCategory.setDescription(postCategoryDetails.getDescription());
        postCategory.setStatus(postCategoryDetails.getStatus());

        PostCategory updatedPostCategory = postCategoryService.savPostCategory(postCategory);
        return ResponseEntity.ok(updatedPostCategory);
    }
}
