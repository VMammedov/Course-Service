package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.User;
import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.repository.UserRepository;
import com.company.courseservice.request.Course.CreateCourseRequest;
import com.company.courseservice.request.Course.UpdateCourseRequest;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import com.company.courseservice.services.impl.CourseServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_course_given_correct_request_then_return_success() {
        //Arrange
        Long subCategoryId = 1L;
        String userEmail = "user@gmail.com";

        CreateCourseRequest request = CreateCourseRequest.builder()
                        .subCategoryId(subCategoryId).build();

        Course savedCourse = Course.builder().id(1L).build();

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(SubCategory.builder()
                .id(subCategoryId).build()));

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(new User()));
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        //Act
        CreateCourseResponse response = courseService.createCourse(request);

        //Assert
        assertNotNull(response);
        assertEquals(savedCourse.getId(), response.getId());
        verify(subCategoryRepository).findById(subCategoryId);
        verify(userRepository).findByEmail(userEmail);
        verify(courseRepository).save(any(Course.class));
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_create_course_given_invalid_sub_category_id_then_throws_data_not_found_exception() {
        //Arrange
        Long subCategoryId = 1L;

        CreateCourseRequest request = CreateCourseRequest.builder()
                .subCategoryId(subCategoryId).build();

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> courseService.createCourse(request));
        verify(subCategoryRepository).findById(subCategoryId);
    }

    @Test
    public void when_call_get_all_course_then_return_success() {
        //Arrange
        List<Course> courses = List.of(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        //Act
        List<CourseResponse> response = courseService.getAllCourse();

        //Assert
        assertNotNull(response);
        verify(courseRepository).findAll();
    }

    @Test
    public void when_call_update_course_by_id_given_correct_request_then_return_success() {
        //Arrange
        Long id = 1L;
        Long subCategoryId = 1L;
        String userEmail = "user@gmail.com";

        Course savedCourse = Course.builder()
                .id(id)
                .subCategory(SubCategory.builder().id(subCategoryId).build())
                .build();

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .subCategoryId(subCategoryId).build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.findCourseByIdAndCreatorEmail(id, userEmail)).thenReturn(
                Optional.of(Course.builder().id(id).build()));
        when(subCategoryRepository.findSubCategoryById(subCategoryId)).thenReturn(
                Optional.of(SubCategory.builder().id(subCategoryId).build()));
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        //Act
        CourseResponse response = courseService.updateCourseById(id, request);

        //Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(courseRepository).findCourseByIdAndCreatorEmail(id, userEmail);
        verify(subCategoryRepository).findSubCategoryById(subCategoryId);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_update_course_by_id_given_invalid_course_id_then_throws_illegal_request_exception() {
        //Arrange
        Long id = 1L;
        Long subCategoryId = 1L;
        String userEmail = "user@gmail.com";

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .subCategoryId(subCategoryId).build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.findCourseByIdAndCreatorEmail(id, userEmail)).thenReturn(
                Optional.empty());

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> courseService.updateCourseById(id, request));
        verify(courseRepository).findCourseByIdAndCreatorEmail(id, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_update_course_by_id_given_invalid_sub_category_id_then_throws_illegal_request_exception() {
        //Arrange
        Long id = 1L;
        Long subCategoryId = 1L;
        String userEmail = "user@gmail.com";

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .subCategoryId(subCategoryId).build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.findCourseByIdAndCreatorEmail(id, userEmail)).thenReturn(
                Optional.of(Course.builder().id(id).build()));
        when(subCategoryRepository.findSubCategoryById(subCategoryId)).thenReturn(
                Optional.empty());

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> courseService.updateCourseById(id, request));
        verify(courseRepository).findCourseByIdAndCreatorEmail(id, userEmail);
        verify(subCategoryRepository).findSubCategoryById(subCategoryId);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_delete_course_by_id_given_correct_id_then_return_success() {
        //Arrange
        Long id = 1L;
        String userEmail = "user@gmail.com";
        Course course = Course.builder().id(id).enabled(true).build();
        Course savedCourse = Course.builder().id(id).enabled(false).build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.findCourseByIdAndCreatorEmail(id, userEmail)).thenReturn(
                Optional.of(course));
        when(courseRepository.save(course)).thenReturn(savedCourse);

        //Act
        courseService.deleteCourseById(id);

        //Assert
        assertFalse(savedCourse.isEnabled());
        verify(courseRepository).findCourseByIdAndCreatorEmail(id, userEmail);
        verify(courseRepository).save(savedCourse);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_delete_course_by_id_given_invalid_id_then_throws_illegal_request_exception() {
        //Arrange
        Long id = 1L;
        String userEmail = "user@gmail.com";

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.findCourseByIdAndCreatorEmail(id, userEmail)).thenReturn(
                Optional.empty());

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> courseService.deleteCourseById(id));
        verify(courseRepository).findCourseByIdAndCreatorEmail(id, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_delete_course_by_id_given_enabled_course_id_then_throws_illegal_request_exception() {
        //Arrange
        Long id = 1L;
        String userEmail = "user@gmail.com";
        Course course = Course.builder().id(id).enabled(false).build();

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        when(courseRepository.findCourseByIdAndCreatorEmail(id, userEmail)).thenReturn(
                Optional.of(course));

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> courseService.deleteCourseById(id));
        verify(courseRepository).findCourseByIdAndCreatorEmail(id, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_get_course_by_id_given_correct_id_then_return_success() {
        //Arrange
        Long id = 1L;
        Course course = Course.builder().id(id).build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        //Act
        CourseResponse response = courseService.getCourseById(id);

        //Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(courseRepository).findById(id);
    }

    @Test
    public void when_call_get_course_by_id_given_invalid_id_then_throws_data_not_found_exception() {
        //Arrange
        Long id = 1L;

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> courseService.getCourseById(id));
        verify(courseRepository).findById(id);
    }
}
