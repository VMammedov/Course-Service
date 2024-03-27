package com.company.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBulkLectureDto {
    private String name;
    private String url;
    private Long durationBySeconds;
    private Integer orderNumber;
    private Long sectionId;
}
