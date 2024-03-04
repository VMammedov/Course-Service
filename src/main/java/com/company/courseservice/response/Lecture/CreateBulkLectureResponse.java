package com.company.courseservice.response.Lecture;

import com.company.courseservice.request.Lecture.CreateLectureRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBulkLectureResponse {
    private List<CreateLectureResponse> lectures;
    private Long courseId;
}
