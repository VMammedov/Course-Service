package com.company.courseservice.repository;

import com.company.courseservice.domain.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<Appeal,Long> {

    Optional<Appeal> findByEmail(String email);
}
