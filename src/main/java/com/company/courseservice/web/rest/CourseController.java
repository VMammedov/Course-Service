package com.company.courseservice.web.rest;

import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseListResponse;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourseResponse createCourse(@RequestBody CreateCourseRequest request){
        return courseService.createCourse(request);
    }

    @GetMapping
    public CourseListResponse getAllCourses(Pageable pageable){
        return courseService.getAllCourses(pageable);
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @GetMapping("/getCourseByName")
    public CourseListResponse getCourseByName(@RequestParam(name = "name") String name, Pageable pageable){
        return courseService.getCoursesByName(name, pageable);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseResponse updateCourseById(@PathVariable("id") Long id, @RequestBody UpdateCourseRequest request){
        return courseService.updateCourseById(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCourseById(@PathVariable("id") Long id){
        courseService.deleteCourseById(id);
    }
}
