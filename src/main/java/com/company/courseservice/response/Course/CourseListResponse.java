package com.company.courseservice.response.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.PaginationInfo;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseListResponse {
    private List<CourseResponse> items;
    private PaginationInfo paginationInfo;
}
