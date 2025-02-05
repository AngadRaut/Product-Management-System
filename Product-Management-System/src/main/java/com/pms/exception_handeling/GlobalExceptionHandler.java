package com.pms.exception_handeling;

import com.pms.custom_exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(){
        log.warn("Resource with the given identifier was not found.");
        log.info("Ensure the provided details are correct.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found. Please check your request details.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException exception){
        log.error("Validation error occurred. Please correct the invalid data.");
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        log.info("List of constraint violations: \n{}", constraintViolations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please follow all the validations. Violations: " + constraintViolations);    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
