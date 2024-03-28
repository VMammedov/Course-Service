package com.company.courseservice.repository;

import com.company.courseservice.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsReviewByCourseIdAndUserId(Long course_id, Long user_id);
    Optional<Review> findByIdAndUserEmail(Long id, String userEmail);
    Page<Review> findAllByUserId(Long id, Pageable pageable);
    Page<Review> findAllByCourseId(Long id, Pageable pageable);
}
