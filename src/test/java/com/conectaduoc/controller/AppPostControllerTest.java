package com.conectaduoc.controller;

import com.conectaduoc.model.AppPost;
import com.conectaduoc.service.AppPostService;
import com.conectaduoc.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppPostControllerTest {

    @Mock
    private AppPostService postService;

    @InjectMocks
    private AppPostController controller;

    @Test
    void listPosts_debeRetornarLista() {
        AppPost post1 = new AppPost();
        AppPost post2 = new AppPost();
        List<AppPost> posts = Arrays.asList(post1, post2);

        when(postService.listPosts()).thenReturn(posts);

        ResponseEntity<List<AppPost>> response = controller.listPost(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void listPosts_porCategoria_debeRetornarLista() {
        Long idCategory = 1L;
        AppPost post1 = new AppPost();
        AppPost post2 = new AppPost();
        List<AppPost> posts = Arrays.asList(post1, post2);

        when(postService.findByCategory(idCategory)).thenReturn(posts);

        ResponseEntity<List<AppPost>> response = controller.listPost(idCategory);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void createPost_debeRetornarPostCreado() {
        AppPost post = new AppPost();
        when(postService.savePost(post)).thenReturn(post);

        ResponseEntity<AppPost> response = controller.createPost(post);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void getPostById_debeRetornarPostPorId() {
        Long idPost = 1L;
        AppPost post = new AppPost();
        post.setIdPost(idPost);

        when(postService.getPost(idPost)).thenReturn(Optional.of(post));

        ResponseEntity<AppPost> response = controller.getPost(idPost);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void deletePost_debeEliminarPost() {
        Long idPost = 1L;
        AppPost fakePost = new AppPost();

        when(postService.getPost(idPost)).thenReturn(Optional.of(fakePost));

        ResponseEntity<Void> response = controller.deletePost(idPost);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService, times(1)).deletePost(idPost);
    }


    @Test
    void listPostsByUser_debeRetornarPostsPorUsuario() {
        int idUser = 1;
        AppPost post1 = new AppPost();
        AppPost post2 = new AppPost();
        List<AppPost> posts = Arrays.asList(post1, post2);

        when(postService.findPostByIdUser(idUser)).thenReturn(posts);

        ResponseEntity<List<AppPost>> response = controller.listPostsByUser(idUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void getPostById_noEncontrado_debeLanzarExcepcion() {
        Long idPost = 1L;

        when(postService.getPost(idPost)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            controller.getPost(idPost);
        });

        assertEquals("El post con ID " + idPost + " no fue encontrado.", exception.getMessage());
    }

    @Test
    void addViewCount_debeSumarVisualizacion() {
        Long idPost = 1L;

        ResponseEntity<Void> response = controller.sumarVisualizacion(idPost);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(postService, times(1)).sumarVisualizacion(idPost);
    }
}
