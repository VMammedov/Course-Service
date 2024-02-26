package com.company.courseservice.services;

import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;

public interface ReviewService {
    CreateReviewResponse createReview(CreateReviewRequest request);
}
