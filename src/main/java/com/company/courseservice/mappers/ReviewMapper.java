package com.company.courseservice.mappers;


import com.company.courseservice.domain.Review;
import com.company.courseservice.request.Review.CreateReviewRequest;
import com.company.courseservice.response.Review.CreateReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "course.id", source = "courseId")
    Review createReviewRequestToReview(CreateReviewRequest request);


    CreateReviewResponse reviewToCreateReviewResponse(Review review);
}
