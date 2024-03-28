package com.company.courseservice.services;

import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseListResponse;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CreateCourseResponse createCourse(CreateCourseRequest request);

    CourseListResponse getAllCourse(Pageable pageable);

    CourseResponse updateCourseById(Long id, UpdateCourseRequest request);

    void deleteCourseById(Long id);

    CourseResponse getCourseById(Long id);

    CourseListResponse getCoursesByName(String name, Pageable pageable);
}
