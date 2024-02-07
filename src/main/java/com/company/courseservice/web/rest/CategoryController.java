package com.company.courseservice.web.rest;

import com.company.courseservice.request.category.CreateCategoryRequest;
import com.company.courseservice.response.category.CategoryResponse;
import com.company.courseservice.response.category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.category.CreateCategoryResponse;
import com.company.courseservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<CreateCategoryResponse> addCategory(CreateCategoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body((categoryService.createCategory(request)));
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<CategoryResponse>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/getCategoriesWithSubCategories")
    public CategoryWithSubCategoriesResponse getCategoriesWithSubCategories(){
        CategoryWithSubCategoriesResponse res = categoryService.getCategoriesWithSubCategories();
        return res;
    }
}
