package com.company.courseservice.repository;

import com.company.courseservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsReviewByCourseIdAndUserId(Long course_id, Long user_id);
    boolean existsReviewsByUserEmail(String email);
    Review findByIdAndUserEmail(Long id, String userEmail);
    List<Review> findAllByUserId(Long id);
    List<Review> findAllByCourseId(Long id);
}
