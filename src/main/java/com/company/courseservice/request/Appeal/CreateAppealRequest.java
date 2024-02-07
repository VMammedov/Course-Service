package com.company.courseservice.request.Appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppealRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
}
