package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Category;
import com.company.courseservice.dto.CategoryDto;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.request.category.CreateCategoryRequest;
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

            categoryRepository.save(category);

            return CreateCategoryResponse.builder().result(true).build();
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }

    @Override
    public CategoryDto getCategory(Long id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            CategoryDto categoryDto = modelMapper.map(category.orElseThrow(() -> new RuntimeException("s")),
                                                      CategoryDto.class);

            return categoryDto;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }

    @Override
    public List<CategoryDto> getCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDto> categoryDtoList = new ArrayList<>();

            for(Category category : categories)
            {
                CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
                categoryDtoList.add(categoryDto);
            }

            return categoryDtoList;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }
}
