package com.conectaduoc.service;

import com.conectaduoc.model.AppPost;
import com.conectaduoc.repository.AppPostRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppPostServiceTest {

    @Mock
    private AppPostRepository repository;

    @InjectMocks
    private AppPostService service;

    @Test
    void listPosts() {
        List<AppPost> posts = new ArrayList<>();
        AppPost post1 = new AppPost();
        post1.setIdPost(1L);
        post1.setTitle("Post 1");
        posts.add(post1);

        AppPost post2 = new AppPost();
        post2.setIdPost(2L);
        post2.setTitle("Post 2");
        posts.add(post2);

        when(repository.findAll()).thenReturn(posts);

        List<AppPost> result = service.listPosts();
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
    }

    @Test
    void getPost() {
        AppPost post = new AppPost();
        post.setIdPost(1L);
        post.setTitle("Post 1");

        when(repository.findById(1L)).thenReturn(Optional.of(post));

        Optional<AppPost> result = service.getPost(1L);
        assertTrue(result.isPresent());
        assertEquals("Post 1", result.get().getTitle());
    }

    @Test
    void savePost() {
        AppPost post = new AppPost();
        post.setIdPost(1L);
        post.setTitle("Post 1");

        when(repository.save(post)).thenReturn(post);

        AppPost result = service.savePost(post);
        assertEquals("Post 1", result.getTitle());
    }

    @Test
    void deletePost() {
        Long postId = 1L;

        doNothing().when(repository).deleteById(postId);

        service.deletePost(postId);
        verify(repository, times(1)).deleteById(postId);
    }

    @Test
    void findPostByIdUser() {
        List<AppPost> posts = new ArrayList<>();
        AppPost post1 = new AppPost();
        post1.setIdPost(1L);
        post1.setIdUser(2L);
        post1.setTitle("Post 1");
        posts.add(post1);

        when(repository.findByIdUser(1)).thenReturn(posts);

        List<AppPost> result = service.findPostByIdUser(1);
        assertEquals(1, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
    }

    @Test
    void findByCategory() {
        List<AppPost> posts = new ArrayList<>();
        AppPost post1 = new AppPost();
        post1.setIdPost(1L);
        post1.setIdCategory(2L);
        post1.setTitle("Post 1");
        posts.add(post1);

        when(repository.findByIdCategory(2L)).thenReturn(posts);

        List<AppPost> result = service.findByCategory(2L);
        assertEquals(1, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
    }

    @Test
    void sumarVisualizacion() {
        AppPost post = new AppPost();
        post.setIdPost(1L);
        post.setViews(5);

        when(repository.findById(1L)).thenReturn(Optional.of(post));
        when(repository.save(post)).thenReturn(post);

        service.sumarVisualizacion(1L);
        assertEquals(6, post.getViews());
        verify(repository, times(1)).save(post);
    }
}
