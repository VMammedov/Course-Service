package com.company.courseservice.repository;

import com.company.courseservice.domain.Appeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRepository extends JpaRepository<Appeal,Long> {

    Page<Appeal> findAllByEmailContains(String email, Pageable pageable);
}
