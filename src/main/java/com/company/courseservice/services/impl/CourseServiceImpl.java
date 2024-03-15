package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.domain.User;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.CourseMapper;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.repository.UserRepository;
import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.AuthUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;

    @Override
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new DataNotFoundException("SubCategory with " + request.getSubCategoryId() + " id not found!"));

        User user = userRepository.findByEmail(AuthUtil.getCurrentUserEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found!"));

        Course course = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .haveCertificate(request.isHaveCertificate())
                .enabled(true)
                .creator(user)
                .subCategory(subCategory)
                .createdDate(new Date())
                .rating((byte) 0)
                .build();

        return CourseMapper.INSTANCE.courseToCreateCourseResponse(courseRepository.save(course));
    }

    @Override
    public List<CourseResponse> getAllCourse() {
        //todo pagination
        List<Course> courses= courseRepository.findAll();
        return courses.stream().map(CourseMapper.INSTANCE::courseToCourseResponse).collect(Collectors.toList());
    }

    @Override
    public CourseResponse updateCourseById(Long id, UpdateCourseRequest request) {

        String currentUserEmail = AuthUtil.getCurrentUserEmail();

        Course course = courseRepository.findCourseByIdAndCreatorEmail(id, currentUserEmail)
                .orElseThrow(() -> new IllegalRequestException("Course does not belong to the user!"));

        SubCategory subCategory = subCategoryRepository.findSubCategoryById(request.getSubCategoryId())
                .orElseThrow(() -> new IllegalRequestException("Sub category not found!"));

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setHaveCertificate(request.isHaveCertificate());
        course.setSubCategory(subCategory);

        return CourseMapper.INSTANCE.courseToCourseResponse(courseRepository.save(course));
    }

    @Override
    public void deleteCourseById(Long id) {
        String currentUserEmail = AuthUtil.getCurrentUserEmail();
        Course course = courseRepository.findCourseByIdAndCreatorEmail(id, currentUserEmail)
                .orElseThrow(() -> new IllegalRequestException("Course does not belong to the user!"));

        if(!course.isEnabled())
            throw new IllegalRequestException("Course already deleted!");

        course.setEnabled(false);
        courseRepository.save(course);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = findCourseById(id);
        return CourseMapper.INSTANCE.courseToCourseResponse(course);
    }

    @Override
    public List<CourseResponse> getCoursesByName(String name) {
        //todo pagination
        List<CourseResponse> courseResponseList;
        List<Course> courseList = courseRepository.findAllByNameLike(name);

        courseResponseList = courseList.stream().map(CourseMapper.INSTANCE::courseToCourseResponse).collect(Collectors.toList());

        return courseResponseList;
    }


    private Course findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Course not found with " + id + " id!"));
    }
}
