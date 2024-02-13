package com.company.courseservice.response.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubCategoryResponse {
    private Long id;
    private Long categoryId;
    private String name;
}
