package com.company.courseservice.services.impl;

import com.company.courseservice.constants.Constants;
import com.company.courseservice.domain.Review;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.ReviewMapper;
import com.company.courseservice.repository.ReviewRepository;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewListResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.response.Review.UpdateReviewResponse;
import com.company.courseservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.AuthUtil;
import utils.PaginationUtil;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = Constants.CacheNames.REVIEW, allEntries = true)
    }
    )
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
    public ReviewListResponse getAllReviews(Pageable pageable) {

        ReviewListResponse response = ReviewListResponse.builder().build();
        Page<Review> reviews = reviewRepository.findAll(pageable);
        response.setItems(reviews.getContent().stream().map(ReviewMapper.INSTANCE::reviewToReviewResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(reviews));
        return response;
    }

    @Override
    @Cacheable(value = Constants.CacheNames.REVIEW, key = "#id")
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Review not found by " + id + " id"));
        return ReviewMapper.INSTANCE.reviewToReviewResponse(review);
    }

    @Override
    @Cacheable(value = Constants.CacheNames.REVIEW, key = "#id")
    public ReviewListResponse getReviewsByUserId(Long id, Pageable pageable) {

        ReviewListResponse response = ReviewListResponse.builder().build();
        Page<Review> reviews = reviewRepository.findAllByUserId(id, pageable);
        response.setItems(reviews.getContent().stream().map(ReviewMapper.INSTANCE::reviewToReviewResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(reviews));
        return response;
    }

    @Override
    @Cacheable(value = Constants.CacheNames.REVIEW, key = "#id")
    public ReviewListResponse getReviewsByCourseId(Long id, Pageable pageable) {

        ReviewListResponse response = ReviewListResponse.builder().build();
        Page<Review> reviews = reviewRepository.findAllByCourseId(id, pageable);
        response.setItems(reviews.getContent().stream().map(ReviewMapper.INSTANCE::reviewToReviewResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(reviews));
        return response;
    }


    @Override
    @Transactional
    @CacheEvict(value = Constants.CacheNames.REVIEW, key = "#id")
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
    @CacheEvict(value = Constants.CacheNames.REVIEW, key = "#id")
    public void deleteReviewById(Long id) {
        String userEmail = AuthUtil.getCurrentUserEmail();

        Review review = reviewRepository.findByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new IllegalRequestException("Review doesn't belong to this User!"));

        reviewRepository.delete(review);
    }
}
