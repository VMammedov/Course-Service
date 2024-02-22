package com.company.courseservice.repository;

import com.company.courseservice.domain.Course;
import com.company.courseservice.domain.Section;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByCourse(Course course);
    List<Section> findAllByNameLike(String name);
    Section findByCourseAndId(Course course, Long id);

    @EntityGraph(attributePaths = {"course"})
    Section findByIdAndCourseId(Long id, Long courseId);
}
