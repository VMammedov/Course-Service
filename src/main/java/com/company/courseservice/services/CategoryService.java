package com.company.courseservice.services;

import com.company.courseservice.dto.CategoryDto;
import com.company.courseservice.request.category.CreateCategoryRequest;
import com.company.courseservice.response.category.CreateCategoryResponse;

import java.util.List;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest request);
    CategoryDto getCategory(Long id);
    List<CategoryDto> getCategories();
}
