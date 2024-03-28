package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Section;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.SectionMapper;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.SectionRepository;
import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.request.Section.UpdateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;
import com.company.courseservice.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.AuthUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    @Override
    public CreatedSectionResponse createSection(CreateSectionRequest request) {
        Long courseId = request.getCourseId();
        String userEmail = AuthUtil.getCurrentUserEmail();

        Course course = courseRepository.findCourseByIdAndCreatorEmail(courseId, userEmail)
                .orElseThrow(()-> new IllegalRequestException("Course not belong to user!"));

        Section section = Section.builder()
                .name(request.getName())
                .course(course)
                .build();
        return SectionMapper.INSTANCE.sectionToCreateSectionResponse(sectionRepository.save(section));
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
        List<SectionResponse> sectionResponseList;
        List<Section> sectionList = sectionRepository.findAllByNameLike(name);

        sectionResponseList = sectionList.stream().map(SectionMapper.INSTANCE::sectionToSectionResponse).collect(Collectors.toList());
        return sectionResponseList;
    }

    @Override
    public SectionResponse updateSectionById(Long id, UpdateSectionRequest request) {
        String userEmail = AuthUtil.getCurrentUserEmail();

        Section section = sectionRepository.findSectionByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new IllegalRequestException("Section not found!"));

        section.setName(request.getName());

        return SectionMapper.INSTANCE.sectionToSectionResponse(sectionRepository.save(section));
    }

    @Override
    public CreatedSectionResponse getSectionById(Long id) {
        Section section = sectionRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Section not found by "+id+" id"));
        return SectionMapper.INSTANCE.sectionToCreateSectionResponse(section);
    }

    @Override
    public void deleteSectionById(Long id) {
        String userEmail = AuthUtil.getCurrentUserEmail();

        Section section = sectionRepository.findSectionByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new IllegalRequestException("Section not found!"));

        sectionRepository.delete(section);
    }
}
