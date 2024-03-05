package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Section;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.mappers.SectionMapper;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.SectionRepository;
import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.request.Section.UpdateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;
import com.company.courseservice.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    @Override
    public CreatedSectionResponse createSection(CreateSectionRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(()->
                new DataNotFoundException("Course not found by "+request.getCourseId()+" id"));
        Section section = Section.builder()
                .name(request.getName())
                .course(course)
                .build();
        section = sectionRepository.save(section);
        return SectionMapper.INSTANCE.sectionToCreateSectionResponse(section);
    }

    @Override
    public List<CreatedSectionResponse> getAllSectionByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->
                new DataNotFoundException("Course not found by "+courseId+" id"));
        List<Section> sections = sectionRepository.findAllByCourse(course);

        return sections.stream().map(SectionMapper.INSTANCE::sectionToCreateSectionResponse).collect(Collectors.toList());
    }
    @Override
    public List<SectionResponse> getSectionByName(String name) {
        List<SectionResponse> sectionResponseList = new ArrayList<>();
        List<Section> sectionList = sectionRepository.findAllByNameLike(name);

        sectionResponseList = sectionList.stream().map(SectionMapper.INSTANCE::sectionToSectionResponse).collect(Collectors.toList());
        return sectionResponseList;
    }
    @Override
    public SectionResponse updateSectionByCourseId(Long courseId,Long id,UpdateSectionRequest request) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->
                new DataNotFoundException("Course not found by "+courseId+" course id"));
        Section section = sectionRepository.findByCourseAndId(course,id);
        Section updateSection = Section.builder()
                .id(section.getId())
                .name(request.getName())
                .course(section.getCourse())
                .build();

        section = sectionRepository.save(updateSection);

        return SectionMapper.INSTANCE.sectionToSectionResponse(section);
    }
    @Override
    public CreatedSectionResponse getSectionById(Long id) {
        Section section = sectionRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Section not found by "+id+" id"));
        return SectionMapper.INSTANCE.sectionToCreateSectionResponse(section);
    }
    @Override
    public void deleteSectionById(Long id) {
        Section section = sectionRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Section not found by "+id+" id"));
        sectionRepository.delete(section);
    }
}
