package com.company.courseservice.services;

import com.company.courseservice.request.Category.CreateCategoryRequest;
import com.company.courseservice.response.Category.CategoryResponse;
import com.company.courseservice.response.Category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.Category.CreateCategoryResponse;

import java.util.List;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse getCategory(Long id);
    List<CategoryResponse> getCategories();
    CategoryWithSubCategoriesResponse getCategoriesWithSubCategories();
}
