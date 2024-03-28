package com.company.courseservice.repository;

import com.company.courseservice.domain.SubCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @EntityGraph(attributePaths = {"category"})
    Optional<SubCategory> findSubCategoryById(Long id);

    List<SubCategory> findSubCategoriesByCategory_Id(Long categoryId);
}
