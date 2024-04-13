package com.company.courseservice.request.Lecture;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLectureRequest {

    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 150, message = "Lecture name must be between 3 and 150 characters!")
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 2000, message = "URL length must be maximum 2000 characters!")
    private String url;

    @Positive
    private Long durationBySeconds;
}
