package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Category;
import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.CategoryRepository;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.request.SubCategory.CreateSubCategoryRequest;
import com.company.courseservice.response.SubCategory.CreateSubCategoryResponse;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;
import com.company.courseservice.services.SubCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateSubCategoryResponse createSubCategory(CreateSubCategoryRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category with " + request.getCategoryId() + " id not found!"));

        SubCategory subCategory = SubCategory.builder().name(request.getName()).category(category).build();

        subCategory = subCategoryRepository.save(subCategory);

        return CreateSubCategoryResponse.builder()
                .id(subCategory.getId())
                .categoryId(category.getId())
                .name(subCategory.getName()).build();
    }

    @Override
    public SubCategoryResponse getSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category with " + id + " id not found!"));

        return modelMapper.map(subCategory, SubCategoryResponse.class);
    }

    @Override
    public List<SubCategoryResponse> getSubCategories() {
        List<SubCategoryResponse> subCategoryResponseList = new ArrayList<>();
        List<SubCategory> subCategories = subCategoryRepository.findAll();

        subCategoryResponseList = subCategories.stream()
                .map(subCategory -> modelMapper.map(subCategory, SubCategoryResponse.class))
                .collect(Collectors.toList());

        return subCategoryResponseList;
    }
}
