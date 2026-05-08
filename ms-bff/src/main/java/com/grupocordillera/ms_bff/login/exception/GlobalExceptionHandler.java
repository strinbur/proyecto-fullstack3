package com.grupocordillera.ms_bff.login.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        String[] order = {"nombre", "apellido", "correo", "password"};

        String error = "";

        for (String field : order) {

            error = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .filter(e -> e.getField().equals(field))
                    .map(e -> e.getDefaultMessage())
                    .findFirst()
                    .orElse(null);

            if (error != null) {
                break;
            }
        }

        if (error == null) {
            error = "Error de validación";
        }

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}