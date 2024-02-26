package com.company.courseservice.web.rest;

import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping("/createReview")
    public CreateReviewResponse createReview(@RequestBody CreateReviewRequest request){
        return service.createReview(request);
    }

}
