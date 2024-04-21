package com.company.courseservice.services.impl;

import com.company.courseservice.constants.Constants;
import com.company.courseservice.domain.RefreshToken;
import com.company.courseservice.domain.User;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.UserRepository;
import com.company.courseservice.repository.UserRoleRepository;
import com.company.courseservice.request.Auth.RefreshTokenRequest;
import com.company.courseservice.request.Auth.SignUpRequest;
import com.company.courseservice.request.Auth.SignInRequest;
import com.company.courseservice.response.Auth.AuthResponse;
import com.company.courseservice.services.AuthService;
import com.company.courseservice.services.JwtService;
import com.company.courseservice.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
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
                .roles(Set.of(userRoleRepository.findByName(Constants.Roles.ROLE_USER)
                        .orElseThrow(() -> new DataNotFoundException("User role not found!")))).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwt).build();
    }

    @Override
    public AuthResponse signIn(SignInRequest request) throws AuthenticationException{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
        return AuthResponse.builder()
                .accessToken(jwt)
                .token(refreshToken.getToken()).build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
     return refreshTokenService.findByToken(request.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user->{
                    String token = jwtService.generateToken(user);
                    return AuthResponse.builder().accessToken(token).token(request.getToken());
                }).orElseThrow(()-> new IllegalArgumentException("token is not valid")).build();

    }
}
