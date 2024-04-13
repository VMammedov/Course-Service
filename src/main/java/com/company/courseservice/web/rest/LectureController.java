package com.company.courseservice.web.rest;

import com.company.courseservice.request.Lecture.CreateBulkLectureRequest;
import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.request.Lecture.UpdateLectureRequest;
import com.company.courseservice.response.Lecture.CreateBulkLectureResponse;
import com.company.courseservice.response.Lecture.CreateLectureResponse;
import com.company.courseservice.response.Lecture.LectureListResponse;
import com.company.courseservice.response.Lecture.LectureResponse;
import com.company.courseservice.services.LectureService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLectureResponse createLecture(@RequestBody @Valid CreateLectureRequest request) {
        return lectureService.createLecture(request);
    }

    @PostMapping("/createBulkLecture")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBulkLectureResponse createBulkLecture(@RequestBody @Valid CreateBulkLectureRequest request) {
        return lectureService.createBulkLecture(request);
    }

    @GetMapping
    public LectureListResponse getLecture(@RequestParam Long courseId,@RequestParam Long sectionId) {
        return lectureService.getLectureList(courseId, sectionId);
    }

    @GetMapping("/{id}")
    public LectureResponse getLectureById(@PathVariable Long id) {
        return lectureService.getLectureById(id);
    }

    @PutMapping
    public LectureResponse updateLecture(@RequestBody @Valid UpdateLectureRequest request) {
        return lectureService.updateLecture(request);
    }

    @DeleteMapping("/{id}")
    public void deleteLecture(@PathVariable Long id) {
        lectureService.deleteLecture(id);
    }

}
