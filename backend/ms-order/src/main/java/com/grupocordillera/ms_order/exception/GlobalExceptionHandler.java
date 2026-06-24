package com.grupocordillera.ms_order.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Validaciones DTO (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Error de validación");

        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Errores de negocio
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Errores desde otros microservicios (Feign)
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeign(FeignException ex) {
        String message = "Error en microservicio";

        try {
            if (ex.contentUTF8() != null && !ex.contentUTF8().isBlank()) {
                message = ex.contentUTF8();
            }
        } catch (Exception ignored) {
        }

        int status = ex.status() > 0 ? ex.status() : 502;

        return ResponseEntity.status(status).body(message);
    }

    /**
     * Fallback general
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}