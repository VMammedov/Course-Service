package com.company.courseservice.services;

import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;

import java.util.List;

public interface CourseService {
    CreateCourseResponse createCourse(CreateCourseRequest request);

    List<CourseResponse> getAllCourse();

    CourseResponse updateCourseById(Long id, UpdateCourseRequest request);

    void deleteCourseById(Long id);

    CourseResponse getCourseById(Long id);

    List<CourseResponse> getCoursesByName(String name);
}
