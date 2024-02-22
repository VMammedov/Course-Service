package com.company.courseservice.exception;

public class IllegalRequestException extends RuntimeException{
    public IllegalRequestException(String message){
        super(message);
    }
}
