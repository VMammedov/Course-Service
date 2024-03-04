package com.company.courseservice.services;

import com.company.courseservice.request.Lecture.CreateBulkLectureRequest;
import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.request.Lecture.UpdateBulkLectureRequest;
import com.company.courseservice.request.Lecture.UpdateLectureRequest;
import com.company.courseservice.response.Lecture.CreateBulkLectureResponse;
import com.company.courseservice.response.Lecture.CreateLectureResponse;
import com.company.courseservice.response.Lecture.LectureListResponse;
import com.company.courseservice.response.Lecture.LectureResponse;

import java.util.List;

public interface LectureService {
    CreateLectureResponse createLecture(CreateLectureRequest request);
    CreateBulkLectureResponse createBulkLecture(CreateBulkLectureRequest request);
    LectureResponse getLectureById(Long id);
    LectureListResponse getLectureList(Long courseId, Long sectionId);
    LectureResponse updateLecture(UpdateLectureRequest request);
    //LectureResponse updateBulkLecture(UpdateBulkLectureRequest request);
    LectureResponse deleteLecture(Long id);
}
