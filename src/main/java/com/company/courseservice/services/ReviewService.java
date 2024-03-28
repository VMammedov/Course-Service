package com.company.courseservice.services;

import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewListResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.response.Review.UpdateReviewResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    CreateReviewResponse createReview(CreateReviewRequest request);

    ReviewListResponse getAllReviews(Pageable pageable);

    ReviewResponse getReviewById(Long id);

    ReviewListResponse getReviewsByUserId(Long id, Pageable pageable);

    ReviewListResponse getReviewsByCourseId(Long id, Pageable pageable);

    UpdateReviewResponse updateReviewById(Long id, UpdateReviewRequest request);

    void deleteReviewById(Long id);
}
