package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Review;
import com.company.courseservice.domain.User;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.repository.ReviewRepository;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.response.Review.UpdateReviewResponse;
import com.company.courseservice.services.impl.ReviewServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_review_given_correct_request_then_return_success() {
        // Arrange
        CreateReviewRequest request = new CreateReviewRequest();
        request.setCourseId(1L);
        request.setUserId(1L);
        User user = new User();
        user.setId(1L);
        Course course = new Course();
        course.setId(1L);
        Review review = new Review();
        review.setUser(user);
        review.setCourse(course);
        when(reviewRepository.existsReviewByCourseIdAndUserId(request.getCourseId(), request.getUserId()))
                .thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        // Act
        CreateReviewResponse response = reviewService.createReview(request);

        // Assert
        assertNotNull(response);
    }

    @Test
    public void when_call_create_review_given_invalid_request_then_throws_illegal_request_exception() {
        // Arrange
        CreateReviewRequest request = new CreateReviewRequest();
        request.setCourseId(1L);
        request.setUserId(1L);

        when(reviewRepository.existsReviewByCourseIdAndUserId(request.getCourseId(), request.getUserId()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(IllegalRequestException.class, () -> reviewService.createReview(request));
    }

    @Test
    public void when_call_get_all_reviews_then_return_success() {
        //Arrange
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(reviews);
        //Act
        List<ReviewResponse> response = reviewService.getAllReviews();

        //Assert
        assertNotNull(response);
    }

    @Test
    public void when_call_get_review_by_id_given_valid_id_then_return_success() {
        //Arrange
        Review existingReview = new Review();
        existingReview.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        //Act
        ReviewResponse response = reviewService.getReviewById(1L);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void when_call_get_review_by_user_id_given_valid_user_id_then_return_success() {
        // Arrange
        Long userId = 123L;
        User user = new User();
        user.setId(userId);
        Review review1 = new Review();
        review1.setUser(user);
        Review review2 = new Review();
        review2.setUser(user);
        List<Review> reviews = Arrays.asList(review1, review2);


        when(reviewRepository.findAllByUserId(userId)).thenReturn(reviews);
        // Act
        List<ReviewResponse> responseList = reviewService.getReviewsByUserId(userId);

        // Assert
        assertNotNull(responseList);
        assertEquals(reviews.size(), responseList.size());

    }

    @Test
    public void when_call_get_review_by_course_id_given_valid_course_id_then_return_success() {
        // Arrange
        Long courseId = 123L;
        Course course = new Course();
        course.setId(courseId);
        Review review1 = new Review();
        review1.setCourse(course);
        Review review2 = new Review();
        review2.setCourse(course);
        List<Review> reviews = Arrays.asList(review1, review2);


        when(reviewRepository.findAllByUserId(courseId)).thenReturn(reviews);

        // Act
        List<ReviewResponse> responseList = reviewService.getReviewsByUserId(courseId);

        // Assert
        assertNotNull(responseList);
        assertEquals(reviews.size(), responseList.size());
    }

    @Test
    public void when_call_update_review_by_id_given_correct_id_then_return_success() {
        //Arrange
        String userEmail = "user@gmail.com";
        Long id = 1L;

        when(reviewRepository.findByIdAndUserEmail(id, userEmail)).thenReturn(Optional.of(new Review()));
        when(reviewRepository.save(any(Review.class))).thenReturn(Review.builder().id(id).build());

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        //Act
        UpdateReviewResponse response = reviewService.updateReviewById(id, new UpdateReviewRequest());

        //Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(reviewRepository).findByIdAndUserEmail(id, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_update_review_by_id_given_invalid_id_then_throws_illegal_request_exception() {
        //Arrange
        String userEmail = "user@gmail.com";
        Long id = 1L;

        when(reviewRepository.findByIdAndUserEmail(id, userEmail)).thenReturn(Optional.empty());

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> reviewService.updateReviewById(id, new UpdateReviewRequest()));
        verify(reviewRepository).findByIdAndUserEmail(id, userEmail);
        authUtilMockedStatic.close();
    }

    @Test
    public void when_call_delete_review_by_id_given_correct_id_then_return_success() {
        //Arrange
        String userEmail = "user@gmail.com";
        Long id = 1L;

        when(reviewRepository.findByIdAndUserEmail(id, userEmail)).thenReturn(Optional.of(new Review()));

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);
        //Act
        reviewService.deleteReviewById(id);

        //Assert
        verify(reviewRepository).findByIdAndUserEmail(id, userEmail);
        authUtilMockedStatic.close();

    }

    @Test
    public void when_call_delete_review_by_id_given_invalid_id_then_throws_illegal_request_exception() {
        //Arrange
        String userEmail = "user@gmail.com";
        Long id = 1L;

        when(reviewRepository.findByIdAndUserEmail(id, userEmail)).thenReturn(Optional.empty());

        MockedStatic<AuthUtil> authUtilMockedStatic = Mockito.mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::getCurrentUserEmail).thenReturn(userEmail);

        //Act & Assert
        assertThrows(IllegalRequestException.class, () -> reviewService.deleteReviewById(id));
        verify(reviewRepository).findByIdAndUserEmail(id, userEmail);
        authUtilMockedStatic.close();

    }
}