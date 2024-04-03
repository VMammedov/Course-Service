package com.company.courseservice.services.impl;

import com.company.courseservice.constants.Constants;
import com.company.courseservice.domain.Category;
import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.mappers.SubCategoryMapper;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.request.SubCategory.CreateSubCategoryRequest;
import com.company.courseservice.response.SubCategory.CreateSubCategoryResponse;
import com.company.courseservice.response.SubCategory.SubCategoryBulkResponse;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;
import com.company.courseservice.services.SubCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @CacheEvict(value = Constants.CacheNames.SUB_CATEGORY_NAMES, allEntries = true)
    public CreateSubCategoryResponse createSubCategory(CreateSubCategoryRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("SubCategory with " + request.getCategoryId() + " id not found!"));

        SubCategory subCategory = SubCategory.builder().name(request.getName()).category(category).build();

        subCategory = subCategoryRepository.save(subCategory);

        return CreateSubCategoryResponse.builder()
                .id(subCategory.getId())
                .categoryId(category.getId())
                .name(subCategory.getName()).build();
    }

    @Override
    @Cacheable(value = Constants.CacheNames.SUB_CATEGORY_NAMES, key = "#id")
    public SubCategoryResponse getSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(id)
                .orElseThrow(() -> new DataNotFoundException("SubCategory with " + id + " id not found!"));

        return SubCategoryMapper.INSTANCE.subCategoryToSubCategoryResponse(subCategory);
    }

    @Override
    @Cacheable(value = Constants.CacheNames.SUB_CATEGORY_NAMES, key = "'category_id_' + #categoryId")
    public SubCategoryBulkResponse getSubCategories(Long categoryId) {

        SubCategoryBulkResponse subCategoryBulkResponse = new SubCategoryBulkResponse();
        List<SubCategory> subCategories = subCategoryRepository.findSubCategoriesByCategory_Id(categoryId);

        subCategoryBulkResponse.setSubCategories(subCategories.stream()
                .map(SubCategoryMapper.INSTANCE::subCategoryToSubCategoryDto)
                .collect(Collectors.toList()));

        subCategoryBulkResponse.setCount(subCategoryBulkResponse.getSubCategories() != null ? subCategories.size() : 0);
        subCategoryBulkResponse.setCategoryId(categoryId);
        return subCategoryBulkResponse;
    }
}
