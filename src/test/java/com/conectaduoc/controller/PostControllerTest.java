package com.conectaduoc.controller;

import com.conectaduoc.model.AppPost;
import com.conectaduoc.service.AppPostService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private AppPostService postService;

    @InjectMocks
    private AppPostController controller;

    @Test
    void listPost_debeRetornarLista() {
        AppPost p1 = new AppPost();
        p1.setIdPost(1L);
        p1.setTitle("Primer post");
        AppPost p2 = new AppPost();
        p2.setIdPost(2L);
        p2.setTitle("Segundo post");

        when(postService.findByCategory(1L)).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<AppPost>> resp = controller.listPost(1L);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(2, resp.getBody().size());
        assertEquals("Primer post", resp.getBody().get(0).getTitle());
        assertEquals("Segundo post", resp.getBody().get(1).getTitle());
    }


    @Test
    void crearPost_debeCrearYRetornar201() {
        AppPost nuevo = new AppPost();
        nuevo.setTitle("Nuevo!");
        nuevo.setIdPost(100L);

        when(postService.savePost(any(AppPost.class))).thenReturn(nuevo);
        ResponseEntity<AppPost> resp = controller.createPost(nuevo);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(nuevo, resp.getBody());
    }

    @Test
    void obtenerPost_debeRetornarSiExiste() {
        AppPost post = new AppPost();
        post.setIdPost(1L);
        when(postService.getPost(1L)).thenReturn(Optional.of(post));

        ResponseEntity<AppPost> resp = controller.getPost(1L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1L, resp.getBody().getIdPost());
    }

    @Test
    void obtenerPost_lanzaExcepcionSiNoExiste() {
        when(postService.getPost(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> controller.getPost(99L));
    }

    @Test
    void eliminarPost_debeEliminarSiExiste() {
        AppPost post = new AppPost();
        post.setIdPost(10L);
        when(postService.getPost(10L)).thenReturn(Optional.of(post));
        doNothing().when(postService).deletePost(10L);

        ResponseEntity<Void> resp = controller.deletePost(10L);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }
    //Aun no implementado
    // @Test
    // void actualizarPost_debeActualizar() {
    //     AppPost original = new AppPost();
    //     original.setIdPost(20L);
    //     original.setTitle("Original");
    //     original.setContent("Contenido");
    //     original.setcreatedDate(null);

    //     AppPost editado = new AppPost();
    //     editado.setTitle("Editado");
    //     editado.setContent("Editado Contenido");
    //     editado.setcreatedDate(null);

    //     when(postService.getPost(20L)).thenReturn(Optional.of(original));
    //     when(postService.savePost(any(AppPost.class))).thenReturn(editado);

    //     ResponseEntity<AppPost> resp = controller.updatePost(20L, editado);

    //     assertEquals(HttpStatus.OK, resp.getStatusCode());
    //     assertEquals("Editado", resp.getBody().getTitle());
    // }
}
