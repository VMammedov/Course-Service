package com.company.courseservice.response.Lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureResponse {
    private Long id;
    private String name;
    private String url;
    private Integer orderNumber;
    private Long durationBySeconds;
}
