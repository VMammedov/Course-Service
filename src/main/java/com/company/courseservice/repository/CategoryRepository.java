package com.company.courseservice.repository;

import com.company.courseservice.domain.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    List<Category> findAllWithoutSubCategories();

    @Override
    @EntityGraph(attributePaths = { "subCategories" })
    List<Category> findAll();
}
