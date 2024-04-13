package com.company.courseservice.request.Section;

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
public class CreateSectionRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100, message = "Message must be between 3 and 100 characters!")
    private String name;

    @NotNull
    private Long courseId;

    @NotNull
    @Positive
    private Long orderNumber;
}
