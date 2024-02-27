package com.company.courseservice.request.Lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLectureRequest {
    private String name;
    private String url;
    private Long durationBySeconds;
    private Integer order;
    private Long courseId;
    private Long sectionId;
}