package com.company.courseservice.request.Lecture;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateLectureRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 150, message = "Lecture name must be between 3 and 150 characters!")
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 2000, message = "URL length must be maximum 2000 characters!")
    private String url;

    @NotNull
    @Positive
    private Long durationBySeconds;

    @NotNull
    @Positive
    private Integer orderNumber;

    @NotNull
    private Long courseId;

    @NotNull
    private Long sectionId;
}
