package com.company.courseservice.mappers;

import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.dto.SubCategoryDto;
import com.company.courseservice.response.SubCategory.SubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    SubCategoryMapper INSTANCE = Mappers.getMapper(SubCategoryMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    SubCategoryResponse subCategoryToSubCategoryResponse(SubCategory subCategory);

    SubCategoryDto subCategoryToSubCategoryDto(SubCategory subCategory);
}
