package com.company.courseservice.web.rest;

import com.company.courseservice.request.SubCategory.CreateSubCategoryRequest;
import com.company.courseservice.response.SubCategory.CreateSubCategoryResponse;
import com.company.courseservice.response.SubCategory.SubCategoryBulkResponse;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;
import com.company.courseservice.services.SubCategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateSubCategoryResponse addSubCategory(@RequestBody CreateSubCategoryRequest request){
        return subCategoryService.createSubCategory(request);
    }

    @GetMapping("/{id}")
    public SubCategoryResponse getSubCategory(@PathVariable Long id){
        return subCategoryService.getSubCategory(id);
    }

    @GetMapping("/getSubCategoriesByCategoryId/{categoryId}")
    public SubCategoryBulkResponse getSubCategories(@PathVariable Long categoryId){
        return subCategoryService.getSubCategories(categoryId);
    }
}
