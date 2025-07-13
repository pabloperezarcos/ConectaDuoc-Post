package com.conectaduoc.controller;

import com.conectaduoc.model.PostCategory;
import com.conectaduoc.service.PostCategoryService;
import com.conectaduoc.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostCategoryControllerTest {

    @Mock
    private PostCategoryService postCategoryService;

    @InjectMocks
    private PostCategoryController controller;

    @Test
    void listPostCategory_debeRetornarLista() {
        PostCategory c1 = new PostCategory(); c1.setIdCategory(1L);
        PostCategory c2 = new PostCategory(); c2.setIdCategory(2L);
        when(postCategoryService.listPostCategory()).thenReturn(Arrays.asList(c1, c2));

        ResponseEntity<List<PostCategory>> resp = controller.listPostCategory();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(2, resp.getBody().size());
    }

    @Test
    void createPostCategory_debeRetornarCategoriaCreada() {
        PostCategory newCategory = new PostCategory();
        when(postCategoryService.savPostCategory(newCategory)).thenReturn(newCategory);

        ResponseEntity<PostCategory> resp = controller.createPostCategory(newCategory);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(newCategory, resp.getBody());
    }

    @Test
    void obtenerPostCategory_debeRetornarCategoriaPorId() {
        Long id = 1L;
        PostCategory category = new PostCategory();
        when(postCategoryService.getPostCategory(id)).thenReturn(Optional.of(category));

        ResponseEntity<PostCategory> resp = controller.obtenerPostCategory(id);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(category, resp.getBody());
    }

    @Test
    void obtenerPostCategory_debeLanzarExcepcionSiNoExiste() {
        Long id = 1L;
        when(postCategoryService.getPostCategory(id)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            controller.obtenerPostCategory(id);
        });

        assertEquals("La categoría de publicación con ID " + id + " no fue encontrada.", thrown.getMessage());
    }
}
