package com.company.courseservice.response.SubCategory;

import com.company.courseservice.dto.SubCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryBulkResponse {

    private List<SubCategoryDto> subCategories;
    private Integer count;
    private Long categoryId;
}
