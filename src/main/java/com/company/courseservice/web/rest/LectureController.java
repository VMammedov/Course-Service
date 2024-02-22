package com.company.courseservice.web.rest;

import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.response.Lecture.CreateLectureResponse;
import com.company.courseservice.services.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLectureResponse createLecture(@RequestBody CreateLectureRequest request){
        return lectureService.createLecture(request);
    }

}
