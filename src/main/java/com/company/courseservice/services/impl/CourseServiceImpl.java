package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.domain.User;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.repository.UserRepository;
import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import utils.AuthUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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

        course = courseRepository.save(course);

        return modelMapper.map(course, CreateCourseResponse.class);
    }

    @Override
    public List<CourseResponse> getAllCourse() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course ->
                modelMapper.map(course, CourseResponse.class)).collect(Collectors.toList());
    }

    @Override
    public CourseResponse updateCourseById(Long id, UpdateCourseRequest request) {
        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new DataNotFoundException("SubCategory with " + request.getSubCategoryId() + " id not found!"));

        User user = userRepository.findByEmail(AuthUtil.getCurrentUserEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        boolean isValidCourseForUser = courseRepository.existsCourseForUser(id,AuthUtil.getCurrentUserEmail());
        if(isValidCourseForUser){
            Course course = findCourseById(id);
            course.setName(request.getName());
            course.setDescription(request.getDescription());
            course.setPrice(request.getPrice());
            course.setHaveCertificate(request.isHaveCertificate());
            course.setSubCategory(subCategory);
            return modelMapper.map(course, CourseResponse.class);
        }else{
            throw new IllegalRequestException("This Course " +
                    id +
                    " not exist " + user);
        }

    }

    @Override
    @SneakyThrows
    public void deleteCourseById(Long id) {
        String userEmail = AuthUtil.getCurrentUserEmail();
        boolean isValidCourseForUser = courseRepository.existsCourseForUser(id,userEmail);
        if(isValidCourseForUser){
            Course course = findCourseById(id);
            if (!course.isEnabled())
                throw new BadRequestException("Course already deleted!");
            course.setEnabled(false);
            courseRepository.save(course);
        }else{
            throw new IllegalRequestException("This Course " +
                    id +
                    " not exist " + userEmail);
        }

    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = findCourseById(id);
        return modelMapper.map(course, CourseResponse.class);
    }

    @Override
    public List<CourseResponse> getCoursesByName(String name) {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        List<Course> courseList = courseRepository.findAllByNameLike(name);

        courseResponseList = courseList.stream().map(course ->
                modelMapper.map(course, CourseResponse.class)).collect(Collectors.toList());

        return courseResponseList;
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Course not found with " + id + " id!"));
    }
}
