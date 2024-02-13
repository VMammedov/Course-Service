package com.company.courseservice.web.rest;

import com.company.courseservice.request.Category.CreateCategoryRequest;
import com.company.courseservice.response.Category.CategoryResponse;
import com.company.courseservice.response.Category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.Category.CreateCategoryResponse;
import com.company.courseservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCategoryResponse addCategory(@RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(request);
    }

    @GetMapping("/getCategory/{id}")
    public CategoryResponse getCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }

    @GetMapping("/getCategories")
    public List<CategoryResponse> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/getCategoriesWithSubCategories")
    public CategoryWithSubCategoriesResponse getCategoriesWithSubCategories(){
        return categoryService.getCategoriesWithSubCategories();
    }
}
