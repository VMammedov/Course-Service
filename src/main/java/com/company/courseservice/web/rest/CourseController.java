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

    private final CourseService service;

    @PostMapping("/createCourse")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourseResponse createCourse(@RequestBody CreateCourseRequest request){
        return service.createCourse(request);
    }

    @GetMapping("/getAllCourse")
    public List<CourseResponse> getAllCourse(){
        return service.getAllCourse();
    }

    @GetMapping("/getCourseById/{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return service.getCourseById(id);
    }
    @GetMapping("getCourseByName")
    public List<CourseResponse> getCourseByName(@RequestParam(name = "name") String name){
        return service.getCoursesByName(name);
    }

    @PutMapping("/updateCourseById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseResponse updateCourseById(@PathVariable Long id, @RequestBody UpdateCourseRequest request){
        return service.updateCourseById(id,request);
    }

    @DeleteMapping("/deleteCourseById/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCourseById(@PathVariable Long id){
         service.deleteCourseById(id);
    }
}
