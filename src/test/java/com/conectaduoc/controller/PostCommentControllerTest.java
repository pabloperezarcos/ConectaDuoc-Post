package com.conectaduoc.controller;

import com.conectaduoc.model.PostComment;
import com.conectaduoc.service.PostCommentService;
import com.conectaduoc.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostCommentControllerTest {

    @Mock
    private PostCommentService comentarioService;

    @InjectMocks
    private PostCommentController controller;

    @Test
    void listComment_debeRetornarLista() {
        PostComment c1 = new PostComment();
        c1.setIdComment(1L);
        c1.setContent("Comentario 1");
        PostComment c2 = new PostComment();
        c2.setIdComment(2L);
        c2.setContent("Comentario 2");

        when(comentarioService.listComment()).thenReturn(Arrays.asList(c1, c2));

        ResponseEntity<List<PostComment>> resp = controller.listComment();

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(2, resp.getBody().size());
    }

    @Test
    void crearComentario_debeCrearYRetornar201() {
        PostComment nuevo = new PostComment();
        nuevo.setContent("Hola!");
        nuevo.setIdComment(100L);

        when(comentarioService.savComment(any(PostComment.class))).thenReturn(nuevo);

        ResponseEntity<PostComment> resp = controller.crearComentario(nuevo);

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(nuevo, resp.getBody());
    }

    @Test
    void obtenerComentario_debeRetornarSiExiste() {
        PostComment comment = new PostComment();
        comment.setIdComment(1L);
        when(comentarioService.getComment(1L)).thenReturn(Optional.of(comment));

        ResponseEntity<PostComment> resp = controller.obtenerComentario(1L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(1L, resp.getBody().getIdComment());
    }

    @Test
    void obtenerComentario_lanzaExcepcionSiNoExiste() {
        when(comentarioService.getComment(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            controller.obtenerComentario(99L);
        });
    }

    @Test
    void eliminarComentario_debeEliminarSiExiste() {
        PostComment comment = new PostComment();
        comment.setIdComment(10L);
        when(comentarioService.getComment(10L)).thenReturn(Optional.of(comment));
        doNothing().when(comentarioService).deleteComment(10L);

        ResponseEntity<Void> resp = controller.eliminarComentario(10L);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }

    @Test
    void actualizarComentario_debeActualizar() {
        PostComment original = new PostComment();
        original.setIdComment(20L);
        original.setContent("Original");
        original.setIdUser(1L);
        original.setIdPost(2L);
        original.setCreatedDate(new Date());

        PostComment editado = new PostComment();
        editado.setContent("Editado");
        editado.setIdUser(1L);
        editado.setIdPost(2L);
        editado.setCreatedDate(new Date());

        when(comentarioService.getComment(20L)).thenReturn(Optional.of(original));
        when(comentarioService.savComment(any(PostComment.class))).thenReturn(editado);

        ResponseEntity<PostComment> resp = controller.actualizarComentario(20L, editado);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Editado", resp.getBody().getContent());
    }
}
