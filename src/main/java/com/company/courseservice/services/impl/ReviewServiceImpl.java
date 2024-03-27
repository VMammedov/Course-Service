package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Review;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.ReviewMapper;
import com.company.courseservice.repository.ReviewRepository;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.response.Review.UpdateReviewResponse;
import com.company.courseservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.AuthUtil;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(ReviewMapper.INSTANCE::reviewToReviewResponse).collect(Collectors.toList());
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Review not found by " + id + " id"));
        return ReviewMapper.INSTANCE.reviewToReviewResponse(review);
    }

    @Override
    public List<ReviewResponse> getReviewsByUserId(Long id) {
        List<Review> reviews = reviewRepository.findAllByUserId(id);
        return reviews.stream()
                .map(ReviewMapper.INSTANCE::reviewToReviewResponse).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewsByCourseId(Long id) {
        List<Review> reviews = reviewRepository.findAllByCourseId(id);
        return reviews.stream()
                .map(ReviewMapper.INSTANCE::reviewToReviewResponse).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UpdateReviewResponse updateReviewById(Long id, UpdateReviewRequest request) {
        String userEmail = AuthUtil.getCurrentUserEmail();

        Review review = reviewRepository.findByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new IllegalRequestException("Review doesn't belong to this User!"));
        review.setRate(request.getRate());
        review.setContent(request.getContent());

        return ReviewMapper.INSTANCE.reviewToUpdateReviewResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public void deleteReviewById(Long id) {
        String userEmail = AuthUtil.getCurrentUserEmail();

        Review review = reviewRepository.findByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new IllegalRequestException("Review doesn't belong to this User!"));

        reviewRepository.delete(review);
    }
}
