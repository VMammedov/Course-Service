package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Section;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.SectionRepository;
import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.request.Section.UpdateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;
import com.company.courseservice.services.impl.SectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class SectionServiceImplTest {

    @Mock
    private SectionRepository sectionRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_section_is_valid_course_id() {
        // Arrange
        CreateSectionRequest request = new CreateSectionRequest();
        request.setCourseId(1L);
        request.setName("Section Name");

        Course mockCourse = new Course();
        mockCourse.setId(1L);
        Section section = new Section();
        section.setCourse(mockCourse);
        section.setName("Section Name");
        when(courseRepository.findById(request.getCourseId())).thenReturn(Optional.of(mockCourse));
        when(sectionRepository.save(any(Section.class))).thenReturn(section);
        // Act
        CreatedSectionResponse response = sectionService.createSection(request);

        // Assert
        assertNotNull(response);


    }

    @Test
    public void when_call_create_section_is_invalid_course_id() {
        // Arrange
        CreateSectionRequest request = new CreateSectionRequest();
        request.setCourseId(1L);
        request.setName("Section Name");

        when(courseRepository.findById(request.getCourseId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.createSection(request));

    }

    @Test
    public void when_call_get_all_section_by_course_id() {
        // Arrange
        Long courseId = 123L;

        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        Section s1 = Section.builder().course(mockCourse).build();
        Section s2 = Section.builder().course(mockCourse).build();
        List<Section> mockSections = Arrays.asList(s1, s2);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        when(sectionRepository.findAllByCourse(mockCourse)).thenReturn(mockSections);

        // Act
        List<CreatedSectionResponse> responseList = sectionService.getAllSectionByCourseId(courseId);

        // Assert
        assertNotNull(responseList);
        assertEquals(mockSections.size(), responseList.size());

    }

    @Test
    public void when_call_get_all_section_by_course_id_with_invalid_course_id() {
        // Arrange
        Long courseId = 123L;

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.getAllSectionByCourseId(courseId));
    }

    @Test
    public void getSectionByName() {
        // Arrange
        String sectionName = "Section";

        Section s1 = Section.builder().name(sectionName).build();
        Section s2 = Section.builder().name(sectionName).build();
        List<Section> mockSections = Arrays.asList(s1, s2);

        when(sectionRepository.findAllByNameLike(sectionName)).thenReturn(mockSections);

        // Act
        List<SectionResponse> responseList = sectionService.getSectionByName(sectionName);

        // Assert
        assertNotNull(responseList);
        assertEquals(mockSections.size(), responseList.size());
    }

    @Test
    public void when_call_update_section_by_course_id() {
        // Arrange
        Long courseId = 123L;
        Long sectionId = 456L;

        UpdateSectionRequest request = new UpdateSectionRequest();
        request.setName("Updated Section Name");

        Course course = new Course();
        course.setId(courseId);

        Section section = new Section();
        section.setName("Test Section");
        section.setCourse(course);
        section.setId(sectionId);

        Section updateSection = new Section();
        updateSection.setId(section.getId());
        updateSection.setName(request.getName());
        updateSection.setCourse(section.getCourse());

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(sectionRepository.findByCourseAndId(course, sectionId)).thenReturn(section);
        when(sectionRepository.save(any(Section.class))).thenReturn(updateSection);

        // Act
        SectionResponse response = sectionService.updateSectionByCourseId(courseId, sectionId, request);

        // Assert
        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
    }

    @Test
    public void when_call_update_section_by_course_id_with_invalid_course_id() {
        // Arrange
        Long courseId = 123L;
        Long sectionId = 456L;

        UpdateSectionRequest request = new UpdateSectionRequest();
        request.setName("Updated Section Name");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.updateSectionByCourseId(courseId, sectionId, request));

    }

    @Test
    public void when_call_get_section_by_id() {
        // Arrange
        Long sectionId = 123L;

        Section mockSection = new Section();
        mockSection.setId(sectionId);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(mockSection));

        // Act
        CreatedSectionResponse response = sectionService.getSectionById(sectionId);

        // Assert
        assertNotNull(response);
        assertEquals(mockSection.getId(), response.getId());
    }

    @Test
    public void when_call_get_section_by_id_not_found() {
        // Arrange
        Long sectionId = 123L;

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.getSectionById(sectionId));
    }

    @Test
    public void when_delete_section_by_id() {
        // Arrange
        Long sectionId = 123L;

        Section mockSection = new Section();
        mockSection.setId(sectionId);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(mockSection));

        // Act
        sectionService.deleteSectionById(sectionId);
    }

    @Test
    public void when_delete_section_by_id_not_found() {
        // Arrange
        Long sectionId = 123L;

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.deleteSectionById(sectionId));
    }
}