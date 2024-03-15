package com.company.courseservice.mappers;

import com.company.courseservice.domain.Category;
import com.company.courseservice.dto.CategoryDto;
import com.company.courseservice.response.Category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponse categoryToCategoryResponse(Category category);

    @Mapping(target = "count", ignore = true)
    CategoryDto categoryToCategoryDto(Category category);
}
