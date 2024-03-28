package com.company.courseservice.response.Appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AppealResponse  {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
    private Date sentDate;
}
