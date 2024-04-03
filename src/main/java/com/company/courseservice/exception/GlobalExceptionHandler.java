package com.company.courseservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandling(RuntimeException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> dataNotFoundExceptionHandling(DataNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterExceptionHandling(MissingServletRequestParameterException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestExceptionHandling(BadRequestException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<?> illegalRequestExceptionHandling(IllegalRequestException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationExceptionHandling(AuthenticationException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.FORBIDDEN);
    }
}
