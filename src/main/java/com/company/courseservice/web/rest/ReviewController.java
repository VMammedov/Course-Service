package com.company.courseservice.web.rest;

import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping("/createReview")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReviewResponse createReview(@RequestBody CreateReviewRequest request){
        return service.createReview(request);
    }

    @GetMapping("/getAllReviews")
    public List<ReviewResponse> getAllReviews(){
        return service.getAllReviews();
    }

    @GetMapping("/getReviewById/{id}")
    public ReviewResponse getReviewById(@PathVariable("id") Long id){
        return service.getReviewById(id);
    }

    @GetMapping("/getReviewsByUserId/{userId}")
    public List<ReviewResponse> getReviewsByUserId(@PathVariable("userId") Long id){
        return service.getReviewsByUserId(id);
    }

    @GetMapping("/getReviewsByCourseId/{courseId}")
    public List<ReviewResponse> getReviewsByCourseId(@PathVariable("courseId") Long id){
        return service.getReviewsByCourseId(id);
    }

    @PutMapping("/updateReviewById/{id}")
    public ReviewResponse updateReviewById(@PathVariable("id") Long id, @RequestBody UpdateReviewRequest request){
        return service.updateReviewById(id,request);
    }

    @DeleteMapping("/deleteReviewById/{id}")
    public void deleteReviewById(@PathVariable("id") Long id){
        service.deleteReviewById(id);
    }
}
