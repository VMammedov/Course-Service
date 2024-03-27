package com.company.courseservice.repository;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByNameLike(String name);

    Optional<Course> findCourseByIdAndCreatorEmail(Long id, String email);

    @Query(value = "SELECT COUNT(c) > 0 FROM Course c JOIN c.sections s JOIN c.creator u " +
            "WHERE c.id = :courseId AND s.id = :sectionId AND u.email = :userEmail")
    boolean existsCourseAndSectionForUser(@Param("courseId") Long courseId,
                                          @Param("sectionId") Long sectionId,
                                          @Param("userEmail") String userEmail);

    @Query("SELECT COUNT(c) > 0 FROM Course c JOIN c.creator u WHERE c.id = :courseId AND u.email = :userEmail")
    boolean existsCourseForUser(Long courseId, String userEmail);

    @Query("SELECT COUNT(c) > 0 FROM Course c JOIN c.sections s WHERE c.id = :courseId AND s.id = :sectionId")
    boolean existsSectionForCourse(Long courseId, Long sectionId);
}
