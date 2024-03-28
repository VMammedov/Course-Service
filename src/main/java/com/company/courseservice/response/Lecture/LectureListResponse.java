package com.company.courseservice.response.Lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class LectureListResponse {

    public LectureListResponse(){
        lectures = new ArrayList<>();
    }

    private List<LectureResponse> lectures;
    private Long sectionId;
}
