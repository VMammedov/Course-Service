package com.company.courseservice.services;

import com.company.courseservice.request.category.CreateCategoryRequest;
import com.company.courseservice.response.category.CategoryResponse;
import com.company.courseservice.response.category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.category.CreateCategoryResponse;

import java.util.List;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse getCategory(Long id);
    List<CategoryResponse> getCategories();
    CategoryWithSubCategoriesResponse getCategoriesWithSubCategories();
}
