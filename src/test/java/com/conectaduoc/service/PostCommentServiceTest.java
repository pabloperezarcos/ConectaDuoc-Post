package com.conectaduoc.service;

import com.conectaduoc.model.PostComment;
import com.conectaduoc.repository.PostCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostCommentServiceTest {

    @Mock
    private PostCommentRepository repository;

    @InjectMocks
    private PostCommentService service;

    @Test
    void listComment() {
        List<PostComment> comments = new ArrayList<>();
        PostComment comment1 = new PostComment();
        comment1.setIdComment(1L);
        comment1.setContent("First comment");
        comments.add(comment1);

        PostComment comment2 = new PostComment();
        comment2.setIdComment(2L);
        comment2.setContent("Second comment");
        comments.add(comment2);

        when(repository.findAll()).thenReturn(comments);

        List<PostComment> result = service.listComment();
        assertEquals(2, result.size());
        assertEquals("First comment", result.get(0).getContent());
    }

    @Test
    void getComment() {
        PostComment comment = new PostComment();
        comment.setIdComment(1L);
        comment.setContent("Test comment");

        when(repository.findById(1L)).thenReturn(Optional.of(comment));

        Optional<PostComment> result = service.getComment(1L);
        assertTrue(result.isPresent());
        assertEquals("Test comment", result.get().getContent());
    }

    @Test
    void saveComment() {
        PostComment comment = new PostComment();
        comment.setContent("New comment");

        when(repository.save(comment)).thenReturn(comment);

        PostComment result = service.savComment(comment);
        assertEquals("New comment", result.getContent());
    }

    @Test
    void deleteComment() {
        Long commentId = 1L;

        service.deleteComment(commentId);
        verify(repository, times(1)).deleteById(commentId);
    }

    @Test
    void getCommentsByPostId() {
        List<PostComment> comments = new ArrayList<>();
        PostComment comment1 = new PostComment();
        comment1.setIdComment(1L);
        comment1.setIdPost(10L);
        comment1.setContent("Comment for post 10");
        comments.add(comment1);

        PostComment comment2 = new PostComment();
        comment2.setIdComment(2L);
        comment2.setIdPost(10L);
        comment2.setContent("Another comment for post 10");
        comments.add(comment2);

        when(repository.findByIdPost(10L)).thenReturn(comments);

        List<PostComment> result = service.getCommentsByPostId(10L);
        assertEquals(2, result.size());
        assertEquals("Comment for post 10", result.get(0).getContent());
    }
}
