package com.company.courseservice.services;

import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.request.Section.UpdateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;

import java.util.List;

public interface SectionService {
    CreatedSectionResponse createSection(CreateSectionRequest request);

    List<CreatedSectionResponse> getAllSectionByCourseId(Long courseId);

    List<SectionResponse> getSectionByName(String name);

    SectionResponse updateSectionByCourseId(Long id, UpdateSectionRequest request);

    CreatedSectionResponse getSectionById(Long id);

    void deleteSectionById(Long id);
}
