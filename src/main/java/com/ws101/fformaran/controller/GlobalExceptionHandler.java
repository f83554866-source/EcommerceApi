package com.ws101.fformaran.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST API.
 * Handles various exceptions and returns appropriate HTTP responses with JSON error messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle MethodArgumentNotValidException (400 Bad Request).
     * Thrown when @Valid validation fails on a DTO (Lab 9 Task 5).
     *
     * @param ex the MethodArgumentNotValidException
     * @param request the web request
     * @return ResponseEntity with structured validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        
        // Extract the default messages we wrote in our DTOs and format them
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> "Field '" + error.getField() + "' " + error.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, Object> body = new HashMap<>();
        // The lab specifically asked for a structured JSON response with "timestamp" and "errors"
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("errors", errors);
        
        // Optionally, you can include the status, error, and path like your other handlers 
        // to maintain consistency across your API responses.
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle RuntimeException (404 Not Found).
     * Typically thrown when a product is not found.
     *
     * @param ex the RuntimeException
     * @param request the web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex, 
            WebRequest request) {
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle DataIntegrityViolationException (400 Bad Request).
     * Thrown when there are constraint violations or invalid data operations.
     *
     * @param ex the DataIntegrityViolationException
     * @param request the web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, 
            WebRequest request) {
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Data Integrity Violation");
        body.put("message", "Invalid data: constraint violation or bad request");
        body.put("details", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle IllegalArgumentException (400 Bad Request).
     * Thrown when invalid arguments are provided.
     *
     * @param ex the IllegalArgumentException
     * @param request the web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex, 
            WebRequest request) {
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Invalid argument: " + ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle all other exceptions (500 Internal Server Error).
     *
     * @param ex the Exception
     * @param request the web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(
            Exception ex, 
            WebRequest request) {
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred");
        body.put("details", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}