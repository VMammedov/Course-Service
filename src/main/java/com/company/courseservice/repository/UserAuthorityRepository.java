package com.company.courseservice.repository;

import com.company.courseservice.domain.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    Optional<UserAuthority> findByAuthority(String auhority);
}
