package com.company.courseservice.repository;

import com.company.courseservice.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllByCourseIdAndSectionId(Long courseId, Long sectionId);

    @Query("SELECT l FROM Lecture l WHERE l.id = :lectureId AND l.course.creator.email = :userEmail")
    Optional<Lecture> findLectureByIdAndForUser(Long lectureId, String userEmail);
}
