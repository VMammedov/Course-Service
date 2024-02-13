package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Category;
import com.company.courseservice.dto.CategoryDto;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.request.Category.CreateCategoryRequest;
import com.company.courseservice.response.Category.CategoryResponse;
import com.company.courseservice.response.Category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.Category.CreateCategoryResponse;
import com.company.courseservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = categoryRepository.save(Category.builder()
                .name(request.getName()).build());

        return CreateCategoryResponse.builder().id(category.getId()).name(category.getName()).build();
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        return modelMapper.map(
                category.orElseThrow(() -> new DataNotFoundException("Category with " + id + " id not found.")),
                CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAllWithoutSubCategories();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        categoryResponseList = categories.stream().map((category -> modelMapper.map(category, CategoryResponse.class))).collect(Collectors.toList());

        return categoryResponseList;
    }

    @Override
    public CategoryWithSubCategoriesResponse getCategoriesWithSubCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList;
        CategoryWithSubCategoriesResponse categoryResponse = new CategoryWithSubCategoriesResponse();

        categoryDtoList = categories.stream().map((category)-> {
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            categoryDto.setCount(categoryDto.getSubCategories().size());
            return categoryDto;
        }).collect(Collectors.toList());

        categoryResponse.setData(categoryDtoList);
        categoryResponse.setCount(categoryDtoList.size());

        return categoryResponse;
    }
}
