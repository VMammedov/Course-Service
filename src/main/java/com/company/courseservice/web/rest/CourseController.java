package com.company.courseservice.web.rest;

import com.company.courseservice.request.course.CreateCourseRequest;
import com.company.courseservice.request.course.UpdateCourseRequest;
import com.company.courseservice.response.course.CourseResponse;
import com.company.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PostMapping("/createCourse")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@RequestBody CreateCourseRequest request){
        return service.createCourse(request);
    }

    @GetMapping("/getAllCourse")
    public List<CourseResponse> getAllCourse(){
        return service.getAllCourse();
    }

    @GetMapping("/getCourseById")
    public CourseResponse getCourseById(@PathVariable("id") Long id){
        return service.getCourseById(id);
    }
    @GetMapping("getCourseByName")
    public CourseResponse getCourseByName(@RequestParam(name = "name") String name){
        return service.getCourseByName(name);
    }

    @PutMapping("/updateCourseById/{id}")
    public CourseResponse updateCourseById(@PathVariable("id") Long id, @RequestBody UpdateCourseRequest request){
        return service.updateCourseById(id,request);
    }

    @DeleteMapping("/deleteCourseById/{id}")
    public void deleteCourseById(@PathVariable("id") Long id){
         service.deleteCourseById(id);
    }


}
