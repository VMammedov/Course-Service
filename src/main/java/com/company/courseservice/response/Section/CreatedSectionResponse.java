package com.company.courseservice.response.Section;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatedSectionResponse {
    private Long id;
    private String name;
    private Long courseId;
}
