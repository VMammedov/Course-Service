package com.company.courseservice.services;

import com.company.courseservice.request.Auth.RefreshTokenRequest;
import com.company.courseservice.request.Auth.SignUpRequest;
import com.company.courseservice.request.Auth.SignInRequest;
import com.company.courseservice.response.Auth.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);
}
