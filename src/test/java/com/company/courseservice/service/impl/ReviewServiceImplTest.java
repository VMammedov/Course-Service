package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Review;
import com.company.courseservice.domain.User;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.ReviewMapper;
import com.company.courseservice.repository.ReviewRepository;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.services.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_review_if_review_does_not_exist() {
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
        when(reviewMapper.reviewToCreateReviewResponse(review)).thenReturn(new CreateReviewResponse());
        // Act
        CreateReviewResponse response = reviewService.createReview(request);

        // Assert
        assertNotNull(response);
    }

    @Test
    void when_call_create_review_if_review_already_exists() {
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
    void when_call_get_all_reviews_then_return_success() {
        //Arrange
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(reviews);
        when(reviewMapper.reviewToReviewResponse(any(Review.class))).thenReturn(new ReviewResponse());

        //Act
        List<ReviewResponse> response = reviewService.getAllReviews();

        //Assert
        assertNotNull(response);
    }

    @Test
    void when_given_id_then_return_success() {
        //Arrange
        Review existingReview = new Review();
        existingReview.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        when(reviewMapper.reviewToReviewResponse(existingReview)).thenReturn(new ReviewResponse());

        //Act
        ReviewResponse response = reviewService.getReviewById(1L);

        //Assert
        assertNotNull(response);
    }

    @Test
    void when_given_user_id_then_return_success() {
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
        when(reviewMapper.reviewToReviewResponse(any(Review.class))).thenReturn(new ReviewResponse());

        // Act
        List<ReviewResponse> responseList = reviewService.getReviewsByUserId(userId);

        // Assert
        assertNotNull(responseList);
        assertEquals(reviews.size(), responseList.size());

    }

    @Test
    void when_given_course_id_then_return_success() {
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
        when(reviewMapper.reviewToReviewResponse(any(Review.class))).thenReturn(new ReviewResponse());

        // Act
        List<ReviewResponse> responseList = reviewService.getReviewsByUserId(courseId);

        // Assert
        assertNotNull(responseList);
        assertEquals(reviews.size(), responseList.size());
    }

    @Test
    void updateReviewById() {

    }

    @Test
    void deleteReviewById() {
    }
}