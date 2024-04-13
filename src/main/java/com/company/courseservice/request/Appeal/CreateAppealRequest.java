package com.company.courseservice.request.Appeal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppealRequest {

    @NotNull
    @NotBlank
    private String fullName;

    @Email
    @NotNull
    @NotBlank
    private String email;

    private String phoneNumber;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 1000, message = "Message must be between 5 and 1000 characters!")
    private String message;
}
