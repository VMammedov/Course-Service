package com.company.courseservice.services;

import com.company.courseservice.request.SignUpRequest;
import com.company.courseservice.request.SignInRequest;
import com.company.courseservice.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);
}
