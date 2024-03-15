package com.company.courseservice.request.Lecture;

import com.company.courseservice.dto.CreateBulkLectureDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBulkLectureRequest {
    private List<CreateBulkLectureDto> lectures;
    private Long courseId;
}
