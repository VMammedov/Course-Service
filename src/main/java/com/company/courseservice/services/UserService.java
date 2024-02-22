package com.company.courseservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    Long findUserIdByUserEmail(String email);

    Long findAuthenticatedUserIdByUserEmail();
}
