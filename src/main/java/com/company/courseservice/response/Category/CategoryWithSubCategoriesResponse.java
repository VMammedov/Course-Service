package com.company.courseservice.response.Category;

import com.company.courseservice.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryWithSubCategoriesResponse {
    private Integer count;
    private List<CategoryDto> data;
}
