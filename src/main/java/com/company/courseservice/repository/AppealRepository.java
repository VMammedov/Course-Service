package com.company.courseservice.repository;

import com.company.courseservice.domain.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealRepository extends JpaRepository<Appeal,Long> {

    List<Appeal> findAllByEmail(String email);
}
