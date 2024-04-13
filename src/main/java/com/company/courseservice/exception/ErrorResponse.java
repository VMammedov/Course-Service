package com.company.courseservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private Date timeStamp;
    private Set<String> error;
    private String details;
}
