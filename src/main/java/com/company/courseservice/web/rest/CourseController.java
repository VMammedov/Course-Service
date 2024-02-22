package com.company.courseservice.web.rest;

import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/createCourse")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourseResponse createCourse(@RequestBody CreateCourseRequest request){
        return courseService.createCourse(request);
    }

    @GetMapping("/getAllCourse")
    public List<CourseResponse> getAllCourse(){
        return courseService.getAllCourse();
    }

    @GetMapping("/getCourseById/{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }
    @GetMapping("getCourseByName")
    public List<CourseResponse> getCourseByName(@RequestParam(name = "name") String name){
        return courseService.getCoursesByName(name);
    }

    @PutMapping("/updateCourseById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseResponse updateCourseById(@PathVariable("id") Long id, @RequestBody UpdateCourseRequest request){
        return courseService.updateCourseById(id,request);
    }

    @DeleteMapping("/deleteCourseById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCourseById(@PathVariable("id") Long id){
        courseService.deleteCourseById(id);
    }
}
