package com.company.courseservice.services;

import com.company.courseservice.request.SubCategory.CreateSubCategoryRequest;
import com.company.courseservice.response.SubCategory.CreateSubCategoryResponse;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    CreateSubCategoryResponse createSubCategory(CreateSubCategoryRequest request);
    SubCategoryResponse getSubCategory(Long id);
    List<SubCategoryResponse> getSubCategories();
}
