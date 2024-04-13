package com.company.courseservice.request.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 70, message = "Username must be between 5 and 70 characters!")
    private String username;

    @NotNull
    @NotBlank
    @Email
    @Size(min = 10, max = 100, message = "Email must be between 5 and 100 characters!")
    private String email;

    @NotNull
    @NotBlank
    //@Pattern(regexp = "")
    @Size(min = 8, max = 100, message = "Password must be between 5 and 100 characters!")
    private String password;

    @NotNull
    @Size(max = 1000, message = "Description must be maximum 1000 characters!")
    private String description;

    @Positive
    private int age;
}
