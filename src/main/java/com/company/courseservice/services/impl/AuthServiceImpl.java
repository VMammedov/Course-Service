package com.company.courseservice.services.impl;

import com.company.courseservice.domain.User;
import com.company.courseservice.repository.UserAuthorityRepository;
import com.company.courseservice.repository.UserRepository;
import com.company.courseservice.request.SignUpRequest;
import com.company.courseservice.request.SignInRequest;
import com.company.courseservice.response.AuthResponse;
import com.company.courseservice.services.AuthService;
import com.company.courseservice.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .age(request.getAge())
                .description(request.getDescription())
                .createdDate(new Date())
                .enabled(true).accountNonLocked(true)
                .accountNonExpired(true).credentialsNonExpired(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(Set.of(userAuthorityRepository.findByAuthority("USER").orElseThrow(()->new RuntimeException("Authority Not found!")))).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwt).build();
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (LockedException ex) {
            // todo User account is locked Exception handling
        } catch (AuthenticationException ex) {
            // todo Exception handling
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwt).build();
    }
}
