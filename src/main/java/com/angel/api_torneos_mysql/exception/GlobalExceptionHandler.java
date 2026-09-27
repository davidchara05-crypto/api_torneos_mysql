package com.angel.api_torneos_mysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<Map<String, Object>> manejarApiExterna(
            ExternalApiException ex
    ) {

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 502,
                        "error", "Bad Gateway",
                        "mensaje", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarArgumento(
            IllegalArgumentException ex
    ) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", "Bad Request",
                        "mensaje", ex.getMessage()
                )
        );
    }
}