package com.company.courseservice.repository;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Section;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByCourse(Course course);
    List<Section> findAllByNameLike(String name);
    Section findByCourseAndId(Course course, Long id);

    @Query("SELECT s FROM Section s JOIN s.course c JOIN c.creator u " +
            "WHERE s.course.id = c.id AND c.creator.email = :userEmail AND s.id = :id")
    Optional<Section> findSectionByIdAndUserEmail(Long id, String userEmail);

    @EntityGraph(attributePaths = {"course"})
    Section findByIdAndCourseId(Long id, Long courseId);
}
