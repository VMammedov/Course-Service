package com.company.courseservice.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestExceptionHandling(Exception exception, WebRequest request)
    {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> internalServerErrorExceptionHandling(Exception exception, WebRequest request)
    {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                , HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<?> illegalRequestExceptionHandling(IllegalRequestException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        exception.getMessage(),
                        request.getDescription(false))
                ,HttpStatus.BAD_REQUEST);
    }
}
