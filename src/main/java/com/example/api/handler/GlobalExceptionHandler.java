package com.example.api.handler;

import com.example.api.common.ConversorData;
import com.example.api.domain.exceptions.ErrorResponse;
import com.example.api.domain.exceptions.ResourceBadRequestException;
import com.example.api.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice

public class GlobalExceptionHandler {

    String dateHour = ConversorData.convertDatetoDateHour(new Date());

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleResourceBadRequestException(ResourceBadRequestException ex) {
        ErrorResponse message = new ErrorResponse(
                dateHour,
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse message = new ErrorResponse(
                dateHour,
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse message = new ErrorResponse(
                dateHour,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

