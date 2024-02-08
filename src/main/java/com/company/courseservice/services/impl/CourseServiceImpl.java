package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.request.course.CreateCourseRequest;
import com.company.courseservice.request.course.UpdateCourseRequest;
import com.company.courseservice.response.course.CourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;



    @Override
    public CourseResponse createCourse(CreateCourseRequest request) {
        Course course = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .createdDate(new Date())
                .haveCertificate(request.isHaveCertificate())
                .rating((byte)0)
                .build();

        course = courseRepository.save(course);

        return modelMapper.map(course, CourseResponse.class);

    }

    @Override
    public List<CourseResponse> getAllCourse() {
        List<Course> courses= courseRepository.findAll();
        return courses.stream().map(course->
                modelMapper.map(course, CourseResponse.class)).collect(Collectors.toList());
    }

    @Override
    public CourseResponse updateCourseById(Long id, UpdateCourseRequest request) {
        Course course = findCourseById(id);
        Course updateCourse = Course.builder()
                .id(course.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .createdDate(new Date())
                .haveCertificate(request.isHaveCertificate())
                .rating((byte)0)
                .build();
        course = courseRepository.save(updateCourse);
        return modelMapper.map(course, CourseResponse.class);
    }

    @Override
    public void deleteCourseById(Long id) {
        Course course = findCourseById(id);

        courseRepository.delete(course);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = findCourseById(id);

        return modelMapper.map(course, CourseResponse.class);
    }

    @Override
    public CourseResponse getCourseByName(String name) {
        Course course = courseRepository.findByName(name).orElseThrow(()->
                new DataNotFoundException("Course not found by "+name+" name"));
        return modelMapper.map(course, CourseResponse.class);
    }

    public Course findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Course not found by"+id+"id"));
    }


}
