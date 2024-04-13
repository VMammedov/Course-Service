package com.company.courseservice.web.rest;

import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.request.Section.UpdateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;
import com.company.courseservice.services.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

import java.util.List;

@Validated
@RestController
@RequestMapping("/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedSectionResponse createSection(@RequestBody @Valid CreateSectionRequest request){
        return service.createSection(request);
    }

    @GetMapping("/getAllSectionsByCourseId/{courseId}")
    public List<CreatedSectionResponse> getAllSectionsByCourseId(@PathVariable("courseId") Long courseId){
        return service.getAllSectionByCourseId(courseId);
    }

    @GetMapping("/getSectionById/{id}")
    public CreatedSectionResponse getSectionById(@PathVariable("id") Long id){
        return service.getSectionById(id);
    }

    @GetMapping("/getSectionByName")
    public List<SectionResponse> getSectionByName(@RequestParam(name = "name") String name){
        return service.getSectionByName(name);
    }

    @PutMapping("/{id}")
    public SectionResponse updateSectionById(@PathVariable("id") Long id, @RequestBody @Valid UpdateSectionRequest request){
        return service.updateSectionById(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteSectionById(@PathVariable("id") Long id){
         service.deleteSectionById(id);
    }
}
