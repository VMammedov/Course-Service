package com.company.courseservice.response.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private boolean haveCertificate;
}
