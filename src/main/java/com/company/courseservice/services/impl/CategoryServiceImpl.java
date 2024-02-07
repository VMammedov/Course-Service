package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Category;
import com.company.courseservice.dto.CategoryDto;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.request.category.CreateCategoryRequest;
import com.company.courseservice.response.category.CategoryResponse;
import com.company.courseservice.response.category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.category.CreateCategoryResponse;
import com.company.courseservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category();

        try {
            category.setName(request.getName());

            category = categoryRepository.save(category);

            return CreateCategoryResponse.builder().id(category.getId()).name(category.getName()).build();
        }
        catch (Exception exception)
        {
            // exception
            return null;
        }
    }

    @Override
    public CategoryResponse getCategory(Long id) {

        CategoryResponse categoryResponse;

        try {
            Optional<Category> category = categoryRepository.findById(id);
            categoryResponse = modelMapper.map(
                    category.orElseThrow(() -> new RuntimeException("s")),
                    CategoryResponse.class);

            return categoryResponse;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }

    @Override
    public List<CategoryResponse> getCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryResponse> categoryResponseList = new ArrayList<>();

            for(Category category : categories)
            {
                CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
                categoryResponseList.add(categoryResponse);
            }

            return categoryResponseList;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }

    @Override
    public CategoryWithSubCategoriesResponse getCategoriesWithSubCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDto> categoryDtoList = new ArrayList<>();
            CategoryWithSubCategoriesResponse categoryResponse = new CategoryWithSubCategoriesResponse();

            for(Category category : categories)
            {
                CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
                categoryDto.setCount(categoryDto.getSubCategories().size());

                categoryDtoList.add(categoryDto);
            }

            categoryResponse.setData(categoryDtoList);
            categoryResponse.setCount(categoryDtoList.size());

            return categoryResponse;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }
}
