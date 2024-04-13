package com.company.courseservice.request.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 150, message = "Course name must be between 3 and 150 characters!")
    private String name;

    @NotNull
    @Size(max = 3000, message = "Description must be maximum 3000 characters!")
    private String description;

    @NotNull
    @PositiveOrZero(message = "Price must be greater than or equal to 0!")
    private Double price;

    private boolean haveCertificate;

    @NotNull
    private Long subCategoryId;
}
