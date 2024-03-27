package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Lecture;
import com.company.courseservice.domain.Section;
import com.company.courseservice.dto.CreateBulkLectureDto;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.LectureRepository;
import com.company.courseservice.request.Lecture.CreateBulkLectureRequest;
import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.request.Lecture.UpdateLectureRequest;
import com.company.courseservice.response.Lecture.CreateBulkLectureResponse;
import com.company.courseservice.response.Lecture.CreateLectureResponse;
import com.company.courseservice.response.Lecture.LectureListResponse;
import com.company.courseservice.response.Lecture.LectureResponse;
import com.company.courseservice.services.impl.LectureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.AuthUtil;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LectureServiceImplTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LectureServiceImpl lectureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_lecture_given_valid_course_id_and_section_id_then_return_success(){
        //Arrange
        Long id = 1L;
        Long courseId = 1L;
        Long sectionId = 1L;
        String userEmail = "user@gmail.com";

        CreateLectureRequest request = CreateLectureRequest.builder()
                .courseId(courseId)
                .sectionId(sectionId)
                .build();

        Lecture savedLecture = Lecture.builder()
                .id(id)
                .course(Course.builder().id(courseId).build())
                .section(Section.builder().id(sectionId).build())
                .build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.existsCourseAndSectionForUser(courseId, sectionId, userEmail)).thenReturn(true);
        when(lectureRepository.save(any(Lecture.class))).thenReturn(savedLecture);

        //Act
        CreateLectureResponse response = lectureService.createLecture(request);

        //Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_create_lecture_given_invalid_course_id_and_section_id_then_throws_illegal_request_exception(){
        //Arrange
        Long courseId = 1L;
        Long sectionId = 1L;
        String userEmail = "user@gmail.com";

        CreateLectureRequest request = CreateLectureRequest.builder()
                .courseId(courseId)
                .sectionId(sectionId)
                .build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.existsCourseAndSectionForUser(courseId, sectionId, userEmail)).thenReturn(false);

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> lectureService.createLecture(request));
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_create_bulk_lecture_given_correct_request_then_return_success() {
        //Arrange
        Long courseId = 1L;
        Long sectionId = 1L;
        Long sectionId2 = 2L;
        String userEmail = "user@gmail.com";

        List<CreateBulkLectureDto> lectureRequests = List.of(CreateBulkLectureDto.builder()
                        .sectionId(sectionId)
                        .build(),
                CreateBulkLectureDto.builder()
                        .sectionId(sectionId)
                        .build(),
                CreateBulkLectureDto.builder()
                        .sectionId(sectionId2)
                        .build());

        CreateBulkLectureRequest request = CreateBulkLectureRequest.builder()
                .courseId(courseId)
                .lectures(lectureRequests)
                .build();

        List<Lecture> lectures = List.of(Lecture.builder()
                        .course(Course.builder().id(courseId).build())
                        .section(Section.builder().id(sectionId).build())
                        .build(),
                Lecture.builder()
                        .course(Course.builder().id(courseId).build())
                        .section(Section.builder().id(sectionId).build())
                        .build(),
                Lecture.builder()
                        .course(Course.builder().id(courseId).build())
                        .section(Section.builder().id(sectionId2).build())
                        .build());

        List<Lecture> savedLectures = List.of(Lecture.builder()
                        .id(1L)
                        .course(Course.builder().id(courseId).build())
                        .section(Section.builder().id(sectionId).build())
                        .build(),
                Lecture.builder()
                        .id(2L)
                        .course(Course.builder().id(courseId).build())
                        .section(Section.builder().id(sectionId).build())
                        .build(),
                Lecture.builder()
                        .id(3L)
                        .course(Course.builder().id(courseId).build())
                        .section(Section.builder().id(sectionId2).build())
                        .build());

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.existsCourseForUser(courseId, userEmail)).thenReturn(true);
        when(courseRepository.existsSectionForCourse(any(Long.class), any(Long.class))).thenReturn(true);
        when(lectureRepository.saveAll(lectures)).thenReturn(savedLectures);

        //Act
        CreateBulkLectureResponse response = lectureService.createBulkLecture(request);

        //Assert
        assertNotNull(response);
        assertEquals(courseId, response.getCourseId());
        assertEquals(lectureRequests.size(), response.getLectures().size());
        verify(courseRepository).existsCourseForUser(courseId, userEmail);
        verify(courseRepository, times(2)).existsSectionForCourse(any(Long.class), any(Long.class));
        verify(lectureRepository).saveAll(lectures);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_create_bulk_lecture_given_invalid_course_id_or_unauthorized_user_then_throws_illegal_request_exception() {
        //Arrange
        Long courseId = 1L;
        String userEmail = "user@gmail.com";

        CreateBulkLectureRequest request = CreateBulkLectureRequest.builder()
                .courseId(courseId)
                .build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.existsCourseForUser(courseId, userEmail)).thenReturn(false);

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> lectureService.createBulkLecture(request));
        verify(courseRepository).existsCourseForUser(courseId, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_create_bulk_lecture_given_invalid_section_id_then_throws_illegal_request_exception() {
        //Arrange
        Long courseId = 1L;
        Long sectionId = 1L;
        String userEmail = "user@gmail.com";

        List<CreateBulkLectureDto> lectureRequests = List.of(CreateBulkLectureDto.builder()
                        .sectionId(sectionId)
                        .build());

        CreateBulkLectureRequest request = CreateBulkLectureRequest.builder()
                .courseId(courseId)
                .lectures(lectureRequests)
                .build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.existsCourseForUser(courseId, userEmail)).thenReturn(true);
        when(courseRepository.existsSectionForCourse(courseId, sectionId)).thenReturn(false);

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> lectureService.createBulkLecture(request));
        verify(courseRepository).existsCourseForUser(courseId, userEmail);
        verify(courseRepository).existsSectionForCourse(courseId, sectionId);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_get_lecture_by_id_given_correct_id_then_return_success() {
        //Arrange
        Long id = 1L;

        when(lectureRepository.findById(id)).thenReturn(Optional.of(Lecture.builder()
                .id(id)
                .build()));

        //Act
        LectureResponse response = lectureService.getLectureById(id);

        //Assert
        assertNotNull(response);
        verify(lectureRepository).findById(id);
    }

    @Test
    public void when_call_get_lecture_by_id_given_invalid_id_then_throws_data_not_found_exception() {
        //Arrange
        Long id = 1L;

        when(lectureRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> lectureService.getLectureById(id));
        verify(lectureRepository).findById(id);
    }

    @Test
    public void when_call_get_lecture_list_given_correct_course_id_and_section_id_then_return_success() {
        //Arrange
        Long courseId = 1L;
        Long sectionId = 1L;
        List<Lecture> lectures = List.of(new Lecture(), new Lecture());

        when(courseRepository.existsSectionForCourse(courseId, sectionId)).thenReturn(true);
        when(lectureRepository.findAllByCourseIdAndSectionId(courseId, sectionId)).thenReturn(lectures);

        //Act
        LectureListResponse response = lectureService.getLectureList(courseId, sectionId);

        //Assert
        assertNotNull(response);
        verify(courseRepository).existsSectionForCourse(courseId, sectionId);
        verify(lectureRepository).findAllByCourseIdAndSectionId(courseId, sectionId);
    }

    @Test
    public void when_call_get_lecture_list_given_invalid_course_id_and_section_id_then_throws_illegal_request_exception() {
        //Arrange
        Long courseId = 1L;
        Long sectionId = 1L;

        when(courseRepository.existsSectionForCourse(courseId, sectionId)).thenReturn(false);

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> lectureService.getLectureList(courseId, sectionId));
        verify(courseRepository).existsSectionForCourse(courseId, sectionId);
    }

    @Test
    public void when_call_update_lecture_given_correct_request_then_return_success() {
        //Arrange
        Long id = 1L;
        UpdateLectureRequest request = UpdateLectureRequest.builder()
                .id(id).build();
        Lecture savedLecture = Lecture.builder().id(id).build();
        String userEmail = "user@gmail.com";

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(lectureRepository.findLectureByIdAndForUser(id, userEmail))
                .thenReturn(Optional.of(savedLecture));
        when(lectureRepository.save(savedLecture)).thenReturn(savedLecture);

        //Act
        LectureResponse response = lectureService.updateLecture(request);

        //Assert
        assertNotNull(response);
        verify(lectureRepository).findLectureByIdAndForUser(id, userEmail);
        verify(lectureRepository).save(savedLecture);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_update_lecture_given_invalid_lecture_id_then_throws_data_not_found_exception() {
        //Arrange
        Long id = 1L;
        UpdateLectureRequest request = UpdateLectureRequest.builder()
                .id(id).build();
        String userEmail = "user@gmail.com";

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(lectureRepository.findLectureByIdAndForUser(id, userEmail))
                .thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> lectureService.updateLecture(request));
        verify(lectureRepository).findLectureByIdAndForUser(id, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_delete_lecture_given_correct_id_then_return_success() {
        //Arrange
        Long id = 1L;
        Lecture lecture = Lecture.builder().id(id).build();
        String userEmail = "user@gmail.com";

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(lectureRepository.findLectureByIdAndForUser(id, userEmail))
                .thenReturn(Optional.of(lecture));
        doNothing().when(lectureRepository).delete(lecture);

        //Act
        LectureResponse response = lectureService.deleteLecture(id);

        //Assert
        assertNotNull(response);
        verify(lectureRepository).findLectureByIdAndForUser(id, userEmail);
        verify(lectureRepository).delete(lecture);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_delete_lecture_given_invalid_id_then_throws_data_not_found_exception() {
        //Arrange
        Long id = 1L;
        String userEmail = "user@gmail.com";

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(lectureRepository.findLectureByIdAndForUser(id, userEmail))
                .thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> lectureService.deleteLecture(id));
        verify(lectureRepository).findLectureByIdAndForUser(id, userEmail);
        authUtilMockedStatic.close();
    }

    // todo validations and authentication tests
}
