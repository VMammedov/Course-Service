package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Category;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.request.Category.CreateCategoryRequest;
import com.company.courseservice.response.Category.CategoryResponse;
import com.company.courseservice.response.Category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.Category.CreateCategoryResponse;
import com.company.courseservice.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_category_given_correct_request_then_return_success() {
        // Arrange
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("TestCategory");
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("TestCategory");
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CreateCategoryResponse response = categoryService.createCategory(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("TestCategory", response.getName());
    }

    @Test
    public void when_given_id_then_return_success() {
        // Arrange
        Long categoryId = 1L;
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("TestCategory");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // Act
        CategoryResponse response = categoryService.getCategory(categoryId);

        // Assert
        assertNotNull(response);
    }

    @Test
    public void when_given_id_not_existed_then_throws_data_not_found_exception() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> categoryService.getCategory(categoryId));
    }

    @Test
    public void when_call_get_categories_then_return_success() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAllWithoutSubCategories()).thenReturn(categories);

        // Act
        List<CategoryResponse> response = categoryService.getCategories();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    public void when_call_get_categories_with_sub_categories_then_return_success() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Integer count = categories.size();
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        CategoryWithSubCategoriesResponse response = categoryService.getCategoriesWithSubCategories();

        // Assert
        assertNotNull(response);
        assertEquals(count, response.getCount());
        assertNotNull(response.getData());
        assertEquals(count, response.getData().size());
    }
}
