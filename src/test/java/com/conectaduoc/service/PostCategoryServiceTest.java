package com.conectaduoc.service;

import com.conectaduoc.model.PostCategory;
import com.conectaduoc.repository.PostCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostCategoryServiceTest {

    @Mock
    private PostCategoryRepository repository;

    @InjectMocks
    private PostCategoryService service;

    @Test
    void listPostCategory() {
        List<PostCategory> categories = new ArrayList<>();
        PostCategory category1 = new PostCategory();
        category1.setIdCategory(1L);
        category1.setName("Category 1");
        categories.add(category1);

        PostCategory category2 = new PostCategory();
        category2.setIdCategory(2L);
        category2.setName("Category 2");
        categories.add(category2);

        when(repository.findAll()).thenReturn(categories);

        List<PostCategory> result = service.listPostCategory();
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
    }

    @Test
    void getPostCategory() {
        PostCategory category = new PostCategory();
        category.setIdCategory(1L);
        category.setName("Category 1");

        when(repository.findById(1L)).thenReturn(Optional.of(category));

        Optional<PostCategory> result = service.getPostCategory(1L);
        assertTrue(result.isPresent());
        assertEquals("Category 1", result.get().getName());
    }

    @Test
    void savePostCategory() {
        PostCategory category = new PostCategory();
        category.setIdCategory(1L);
        category.setName("Category 1");

        when(repository.save(category)).thenReturn(category);

        PostCategory result = service.savPostCategory(category);
        assertEquals("Category 1", result.getName());
    }

    @Test
    void deletePostCategory() {
        Long categoryId = 1L;

        doNothing().when(repository).deleteById(categoryId);

        service.deletePostCategory(categoryId);
        verify(repository, times(1)).deleteById(categoryId);
    }
}
