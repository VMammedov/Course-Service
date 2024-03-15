package com.company.courseservice.mappers;

import com.company.courseservice.domain.Review;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.request.Review.UpdateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import com.company.courseservice.response.Review.ReviewResponse;
import com.company.courseservice.response.Review.UpdateReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    Review createReviewRequestToReview(CreateReviewRequest request);

    CreateReviewResponse reviewToCreateReviewResponse(Review review);

    ReviewResponse reviewToReviewResponse(Review review);

    UpdateReviewResponse reviewToUpdateReviewResponse(Review review);

}
