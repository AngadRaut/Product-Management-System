package com.pms.exception_handeling;

import com.pms.custom_exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
        log.error("Resource with the given identifier was not found.");
        log.warn("Ensure the provided details are correct.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found. Please check your request details.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException exception){
        log.error("Validation error occurred. Please correct the invalid data.");
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
           log.warn("List of constraint violations: \n{}", constraintViolations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please follow all the validations. Violations: " + constraintViolations);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Data Integrity Violation: Please enter the non duplicate record.."+ex.getMessage());
        // Check if the exception is due to duplicate key violation
        if (ex.getMessage().contains("duplicate key value violates unique constraint")) {
            // Return a custom error message
            return new ResponseEntity<>("Email address already exists. Please use a different one.", HttpStatus.BAD_REQUEST);
        }

//        // For other DataIntegrityViolationExceptions, return a generic error message
        return new ResponseEntity<>("Data integrity violation occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred! {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}

 class ErrorResponse {
    private String message;
    private String details;

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
    // Getters and setters
     public String getMessage() {
         return message;
     }

     public void setMessage(String message) {
         this.message = message;
     }

     public String getDetails() {
         return details;
     }

     public void setDetails(String details) {
         this.details = details;
     }
 }
