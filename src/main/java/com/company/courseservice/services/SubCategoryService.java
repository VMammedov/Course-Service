package com.company.courseservice.services;

import com.company.courseservice.request.SubCategory.CreateSubCategoryRequest;
import com.company.courseservice.response.SubCategory.CreateSubCategoryResponse;
import com.company.courseservice.response.SubCategory.SubCategoryBulkResponse;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;

public interface SubCategoryService {
    CreateSubCategoryResponse createSubCategory(CreateSubCategoryRequest request);
    SubCategoryResponse getSubCategory(Long id);
    SubCategoryBulkResponse getSubCategories(Long categoryId);
}
