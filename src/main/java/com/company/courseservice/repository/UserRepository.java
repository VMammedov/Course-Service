package com.company.courseservice.repository;

import com.company.courseservice.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findByUsername(String userName);
}