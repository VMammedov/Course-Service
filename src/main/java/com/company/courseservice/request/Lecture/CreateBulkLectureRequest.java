package com.company.courseservice.request.Lecture;

import com.company.courseservice.dto.CreateBulkLectureDto;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private List<CreateBulkLectureDto> lectures;

    @NotNull
    private Long courseId;
}
