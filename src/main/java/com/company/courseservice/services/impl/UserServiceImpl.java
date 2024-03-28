package com.company.courseservice.services.impl;

import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.services.UserService;
import lombok.RequiredArgsConstructor;
import com.company.courseservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " email not found!"));
    }

    @Override
    public Long findUserIdByUserEmail(String email) {
        Long id = userRepository.findIdByEmail(email);
        if(id != 0)
            return id;
        throw new DataNotFoundException("User with " + email + " email not found!");
    }
}
