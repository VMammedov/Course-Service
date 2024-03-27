package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Category;
import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.request.SubCategory.CreateSubCategoryRequest;
import com.company.courseservice.response.SubCategory.CreateSubCategoryResponse;
import com.company.courseservice.response.SubCategory.SubCategoryBulkResponse;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;
import com.company.courseservice.services.impl.SubCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubCategoryServiceImplTest {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SubCategoryServiceImpl subCategoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_sub_category_given_correct_request_then_return_success(){
        //Arrange
        Long categoryId = 1L;

        CreateSubCategoryRequest request = CreateSubCategoryRequest.builder()
                .categoryId(categoryId)
                .name("TestSubCategory")
                .build();

        SubCategory savedSubCategory = SubCategory.builder()
                .id(1L)
                .category(Category.builder().id(categoryId).build())
                .name("TestSubCategory")
                .build();

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Category()));
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(savedSubCategory);

        //Act

        CreateSubCategoryResponse response = subCategoryService.createSubCategory(request);

        //Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("TestSubCategory", response.getName());
    }

    @Test
    public void when_call_create_sub_category_given_invalid_category_then_throws_data_not_found_exception()
    {
        //Arrange
        Long categoryId = 1L;

        CreateSubCategoryRequest request = CreateSubCategoryRequest.builder()
                .categoryId(categoryId)
                .name("TestSubCategory")
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(new SubCategory());

        //Act & Assert
        assertThrows(DataNotFoundException.class,() -> subCategoryService.createSubCategory(request));
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    public void when_call_get_sub_category_given_correct_id_then_return_success(){
        //Arrange
        Long id = 1L;
        SubCategory existingSubCategory = SubCategory.builder()
                                .id(id)
                                .name("TestSubCategory").build();

        when(subCategoryRepository.findSubCategoryById(id)).thenReturn(Optional.of(existingSubCategory));

        //Act
        SubCategoryResponse response = subCategoryService.getSubCategory(id);

        //Assert
        assertNotNull(response);
        verify(subCategoryRepository).findSubCategoryById(id);
    }

    @Test
    public void when_call_get_sub_category_given_invalid_id_then_throws_data_not_found_exception(){
        //Arrange
        Long id = 1L;
        when(subCategoryRepository.findSubCategoryById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> subCategoryService.getSubCategory(id));
        verify(subCategoryRepository).findSubCategoryById(id);
    }

    @Test
    public void when_call_get_sub_categories_given_correct_category_id_then_return_success(){
        //Arrange
        Long categoryId = 1L;
        List<SubCategory> subCategories = List.of(new SubCategory(), new SubCategory());
        when(subCategoryRepository.findSubCategoriesByCategory_Id(categoryId)).thenReturn(subCategories);

        //Act
        SubCategoryBulkResponse response = subCategoryService.getSubCategories(categoryId);

        //Arrange
        assertNotNull(response);
        assertEquals(subCategories.size(), response.getCount());
        verify(subCategoryRepository).findSubCategoriesByCategory_Id(categoryId);
    }
}
