package com.company.courseservice.web.rest;

import com.company.courseservice.request.SignUpRequest;
import com.company.courseservice.request.SignInRequest;
import com.company.courseservice.response.AuthResponse;
import com.company.courseservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public AuthResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public AuthResponse authenticateAndGetToken(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }
}
