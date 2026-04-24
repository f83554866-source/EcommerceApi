package com.ws101.fformaran.EcommerceApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleError(RuntimeException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "Not Found",
                "message", ex.getMessage()
        ));
    }
}