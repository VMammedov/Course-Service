package com.company.courseservice.services;

import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewResponse;

import java.util.List;

public interface ReviewService {
    CreateReviewResponse createReview(CreateReviewRequest request);

    List<ReviewResponse> getAllReviews();

    ReviewResponse getReviewById(Long id);

    List<ReviewResponse> getReviewsByUserId(Long id);

    List<ReviewResponse> getReviewsByCourseId(Long id);

    ReviewResponse updateReviewById(Long id, UpdateReviewRequest request);

    void deleteReviewById(Long id);
}
