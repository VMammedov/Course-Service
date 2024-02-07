package com.company.courseservice.dto;

import com.company.courseservice.domain.SubCategory;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String name;

    private Integer count;

    @OneToMany(mappedBy = "category")
    private List<SubCategory> subCategories;
}
