package com.company.courseservice.web.rest;

import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.request.Section.UpdateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;
import com.company.courseservice.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

    @PostMapping("/createSection")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedSectionResponse createSection(@RequestBody CreateSectionRequest request){
        return service.createSection(request);
    }

    @GetMapping("/getAllSectionByCourseId/{courseId}")
    public List<CreatedSectionResponse> getAllSectionByCourseId(@PathVariable("courseId") Long courseId){
        return service.getAllSectionByCourseId(courseId);
    }

    @GetMapping("getSectionById/{id}")
    public CreatedSectionResponse getSectionById(@PathVariable("id") Long id){
        return service.getSectionById(id);
    }

    @GetMapping("/getSectionByName")
    public List<SectionResponse> getSectionByName(@RequestParam(name = "name") String name){
        return service.getSectionByName(name);
    }

    @PutMapping("/updateSectionByCourseId/{id}")
    public SectionResponse updateSectionByCourseId(@PathVariable("id") Long id, @RequestBody UpdateSectionRequest request){
        return service.updateSectionByCourseId(id,request);
    }

    @DeleteMapping("deleteSectionById/{id}")
    public void deleteSectionById(@PathVariable("id") Long id){
         service.deleteSectionById(id);
    }
}
