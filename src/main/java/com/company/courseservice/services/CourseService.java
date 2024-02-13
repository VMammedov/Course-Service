package com.company.courseservice.services;

import com.company.courseservice.request.course.CreateCourseRequest;
import com.company.courseservice.request.course.UpdateCourseRequest;
import com.company.courseservice.response.course.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CreateCourseRequest request);

    List<CourseResponse> getAllCourse();

    CourseResponse updateCourseById(Long id, UpdateCourseRequest request);

    void deleteCourseById(Long id);

    CourseResponse getCourseById(Long id);

    CourseResponse getCourseByName(String name);
}
