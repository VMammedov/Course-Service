package com.company.courseservice.request.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseRequest {
    private String name;
    private String description;
    private Double price;
    private boolean haveCertificate;
}
