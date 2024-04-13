package com.company.courseservice.request.Review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequest {

    @NotNull
    @NotBlank
    @Size(max = 500, message = "Comment length must be 500 characters!")
    private String content;

    @NotNull
    @Size(min = 0, max = 5)
    private byte rate;
}
