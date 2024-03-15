package com.company.courseservice.request.Lecture;

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
    private String name;
    private String url;
    private Long durationBySeconds;
    private Integer orderNumber;
    private Long courseId;
    private Long sectionId;
}
