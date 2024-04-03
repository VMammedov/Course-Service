package com.company.courseservice.web.rest;

import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewListResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.response.Review.UpdateReviewResponse;
import com.company.courseservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReviewResponse createReview(@RequestBody CreateReviewRequest request) {
        return service.createReview(request);
    }

    @GetMapping
    public ReviewListResponse getAllReviews(Pageable pageable) {
        return service.getAllReviews(pageable);
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable("id") Long id) {
        return service.getReviewById(id);
    }

    @GetMapping("/getReviewsByUserId/{userId}")
    public ReviewListResponse getReviewsByUserId(@PathVariable("userId") Long id, Pageable pageable) {
        return service.getReviewsByUserId(id, pageable);
    }

    @GetMapping("/getReviewsByCourseId/{courseId}")
    public ReviewListResponse getReviewsByCourseId(@PathVariable("courseId") Long id, Pageable pageable) {
        return service.getReviewsByCourseId(id, pageable);
    }

    @PutMapping("/{id}")
    public UpdateReviewResponse updateReviewById(@PathVariable("id") Long id, @RequestBody UpdateReviewRequest request) {
        return service.updateReviewById(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable("id") Long id) {
        service.deleteReviewById(id);
    }
}
