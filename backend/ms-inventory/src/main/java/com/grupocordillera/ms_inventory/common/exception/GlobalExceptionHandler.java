package com.grupocordillera.ms_inventory.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {

        Map<String, Object> response = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        response.put("status", 400);
        response.put("errores", errors);

        return ResponseEntity
                .badRequest()
                .body(response);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex
    ) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", ex.getStatusCode().value());
        response.put("errores", List.of(ex.getReason()));

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(response);
    }

    // Fallback general para cualquier otra excepción no manejada específicamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            Exception ex
    ) {

        ex.printStackTrace();

        Map<String, Object> response = new HashMap<>();

        response.put("status", 500);
        response.put(
                "errores",
                List.of("Error interno del servidor")
        );

        return ResponseEntity
                .internalServerError()
                .body(response);
    }
}