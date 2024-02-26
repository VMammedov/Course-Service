package com.company.courseservice.repository;

import com.company.courseservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsReviewByCourseIdAndUserId(Long course_id, Long user_id);
}
