package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Review;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.ReviewMapper;
import com.company.courseservice.repository.ReviewRepository;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public CreateReviewResponse createReview(CreateReviewRequest request) {
        boolean usedReviewForThisCourseAndUser =
                reviewRepository.existsReviewByCourseIdAndUserId(request.getCourseId(), request.getUserId());
        if (!usedReviewForThisCourseAndUser) {
            Review review = reviewRepository.save(ReviewMapper.INSTANCE.createReviewRequestToReview(request));
            return ReviewMapper.INSTANCE.reviewToCreateReviewResponse(review);
        } else {
            throw new IllegalRequestException("A review has been created in this course " +
                    request.getCourseId() +
                    " with this user ID" + request.getUserId());
        }
    }
}
