package com.company.courseservice.services.impl;

import com.company.courseservice.constants.Constants;
import com.company.courseservice.domain.Category;
import com.company.courseservice.dto.CategoryDto;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.mappers.CategoryMapper;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.request.Category.CreateCategoryRequest;
import com.company.courseservice.response.Category.CategoryResponse;
import com.company.courseservice.response.Category.CategoryWithSubCategoriesResponse;
import com.company.courseservice.response.Category.CreateCategoryResponse;
import com.company.courseservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = Constants.CacheNames.CATEGORY_NAMES, allEntries = true),
            @CacheEvict(value = Constants.CacheNames.CATEGORY_NAMES_WITH_SUB, allEntries = true)
    })
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = categoryRepository.save(Category.builder()
                .name(request.getName()).build());

        return CreateCategoryResponse.builder().id(category.getId()).name(category.getName()).build();
    }

    @Override
    @Cacheable(value = Constants.CacheNames.CATEGORY_NAMES, key = "#id")
    public CategoryResponse getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        return CategoryMapper.INSTANCE.categoryToCategoryResponse(
                category.orElseThrow(() -> new DataNotFoundException("Category with " + id + " id not found."))
        );
    }

    @Override
    @Cacheable(value = Constants.CacheNames.CATEGORY_NAMES, key = "'categories_' + T(java.time.LocalDateTime).now()")
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAllWithoutSubCategories();
        List<CategoryResponse> categoryResponseList;

        categoryResponseList = categories.stream().map(CategoryMapper.INSTANCE::categoryToCategoryResponse).collect(Collectors.toList());

        return categoryResponseList;
    }

    @Override
    //@Cacheable(value = Constants.CacheNames.CATEGORY_NAMES_WITH_SUB, key = "'categories_with_sub_categories_' + T(java.time.LocalDateTime).now()")
    public CategoryWithSubCategoriesResponse getCategoriesWithSubCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList;
        CategoryWithSubCategoriesResponse categoryResponse = new CategoryWithSubCategoriesResponse();

        categoryDtoList = categories.stream().map((category)-> {
            CategoryDto categoryDto = CategoryMapper.INSTANCE.categoryToCategoryDto(category);
            categoryDto.setCount(categoryDto.getSubCategories() != null ? categoryDto.getSubCategories().size() : 0);
            return categoryDto;
        }).collect(Collectors.toList());

        categoryResponse.setData(categoryDtoList);
        categoryResponse.setCount(categoryDtoList.size());

        return categoryResponse;
    }
}
