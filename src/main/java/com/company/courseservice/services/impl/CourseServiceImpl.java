package com.company.courseservice.services.impl;

import com.company.courseservice.constants.Constants;
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
import com.company.courseservice.response.Course.CourseListResponse;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.AuthUtil;
import utils.PaginationUtil;

import java.util.Date;
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
    public CourseListResponse getAllCourses(Pageable pageable) {

        CourseListResponse response = CourseListResponse.builder().build();
        Page<Course> courses = courseRepository.findAll(pageable);
        response.setItems(courses.getContent().stream().map(CourseMapper.INSTANCE::courseToCourseResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(courses));
        return response;
    }

    @Override
    @Cacheable(value = Constants.CacheNames.COURSE, key = "#id")
    public CourseResponse getCourseById(Long id) {
        Course course = findCourseById(id);
        return CourseMapper.INSTANCE.courseToCourseResponse(course);
    }

    @Override
    public CourseListResponse getCoursesByName(String name, Pageable pageable) {

        CourseListResponse response = CourseListResponse.builder().build();
        Page<Course> courses = courseRepository.findAllByNameContains(name, pageable);
        response.setItems(courses.getContent().stream().map(CourseMapper.INSTANCE::courseToCourseResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(courses));
        return response;
    }

    @Override
    @CacheEvict(value = Constants.CacheNames.COURSE, key = "#id")
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
    @CacheEvict(value = Constants.CacheNames.COURSE, key = "#id")
    public void deleteCourseById(Long id) {
        String currentUserEmail = AuthUtil.getCurrentUserEmail();
        Course course = courseRepository.findCourseByIdAndCreatorEmail(id, currentUserEmail)
                .orElseThrow(() -> new IllegalRequestException("Course does not belong to the user!"));

        if(!course.isEnabled())
            throw new IllegalRequestException("Course already deleted!");

        course.setEnabled(false);
        courseRepository.save(course);
    }

    private Course findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Course not found with " + id + " id!"));
    }
}
