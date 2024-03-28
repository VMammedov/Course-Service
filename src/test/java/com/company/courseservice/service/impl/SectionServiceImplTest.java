package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Section;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.AuthUtil;

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
    public void when_call_create_section_given_valid_course_id_then_return_success() {
        Long courseId = 1L;
        Long id = 1L;
        String userEmail = "test@gmail.com";

        //Arrange
        CreateSectionRequest request = CreateSectionRequest.builder()
                .courseId(courseId)
                .name("Section name").build();
        Section savedSection = Section.builder()
                .id(id)
                .name("Section name").build();
        when(courseRepository.findCourseByIdAndCreatorEmail(courseId, userEmail)).thenReturn(Optional.of(new Course()));
        when(sectionRepository.save(any(Section.class))).thenReturn(savedSection);
        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);
        //Act
        CreatedSectionResponse response = sectionService.createSection(request);
        //Assert
        assertNotNull(response);
        assertEquals(id,response.getId());
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_create_section_given_invalid_course_id_then_throws_illegal_request_exception() {
        Long courseId = 1L;
        String userEmail = "test@gmail.com";
        // Arrange
        CreateSectionRequest request = new CreateSectionRequest();
        request.setCourseId(1L);
        request.setName("Section Name");

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);
        when(courseRepository.findCourseByIdAndCreatorEmail(courseId,userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalRequestException.class, () -> sectionService.createSection(request));
        authUtilMockedStatic.close();

    }

    @Test
    public void when_call_get_all_section_by_course_id_given_valid_course_id_then_return_success() {
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
    public void when_call_get_all_section_by_course_id_given_invalid_course_id_then_throws_data_not_found_exception() {
        // Arrange
        Long courseId = 123L;

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.getAllSectionByCourseId(courseId));
    }

    @Test
    public void when_call_get_section_by_name_given_valid_name_then_return_success() {
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
    public void when_call_update_section_by_id_given_valid_id_then_return_success() {
        // Arrange
        Long sectionId = 456L;
        String userEmail = "test@gmail.com";
        UpdateSectionRequest request = new UpdateSectionRequest();
        request.setName("Updated Section Name");
        Section savedSection = Section.builder().id(sectionId).name("Updated Section Name").build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(sectionRepository.findSectionByIdAndUserEmail(sectionId,userEmail)).thenReturn(Optional.of(savedSection));
        when(sectionRepository.save(savedSection)).thenReturn(savedSection);

        //Act
        SectionResponse response = sectionService.updateSectionById(sectionId,request);
        //Assert
        assertNotNull(response);
        authUtilMockedStatic.close();


    }

    @Test
    public void when_call_update_section_by_id_given_invalid_id_then_throws_data_not_found_exception() {
        // Arrange
        Long sectionId = 456L;
        String userEmail = "test@gmail.com";
        UpdateSectionRequest request = new UpdateSectionRequest();
        request.setName("Updated Section Name");
        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(sectionRepository.findSectionByIdAndUserEmail(sectionId,userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalRequestException.class, () -> sectionService.updateSectionById(sectionId, request));
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_get_section_by_id_given_valid_id_then_return_success() {
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
    public void when_call_get_section_by_id_given_invalid_id_then_throws_data_not_found_exception() {
        // Arrange
        Long sectionId = 123L;

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> sectionService.getSectionById(sectionId));
    }

    @Test
    public void when_delete_section_by_id_given_valid_id_then_return_success() {
        // Arrange
        Long sectionId = 123L;
        String userEmail = "test@gmail.com";
        Section mockSection = new Section();
        mockSection.setId(sectionId);

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(sectionRepository.findSectionByIdAndUserEmail(sectionId,userEmail)).thenReturn(Optional.of(mockSection));

        // Act
        sectionService.deleteSectionById(sectionId);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_delete_section_by_id_given_invalid_id_then_throws_illegal_request_exception() {
        // Arrange
        Long sectionId = 123L;
        String userEmail = "test@gmail.com";
        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(sectionRepository.findSectionByIdAndUserEmail(sectionId,userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalRequestException.class, () -> sectionService.deleteSectionById(sectionId));
        authUtilMockedStatic.close();
    }
}