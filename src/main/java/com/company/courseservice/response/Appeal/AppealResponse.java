package com.company.courseservice.response.Appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppealResponse  {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
    private Date sentDate;
}
