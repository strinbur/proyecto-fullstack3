package com.grupocordillera.ms_bff.exception;

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
     * Validaciones del frontend
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        String[] order = {
                "nombre",
                "apellido",
                "correo",
                "password",
                "codigo",
                "marca",
                "precio",
                "cantidad",
                "categoria"
        };

        String error = null;

        for (String field : order) {

            error = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .filter(e -> e.getField().equals(field))
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse(null);

            if (error != null) {
                break;
            }
        }

        if (error == null) {

            error = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Error de validación");
        }

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    /**
     * Errores de negocio del BFF
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(
            RuntimeException ex
    ) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    /**
     * Errores provenientes de microservicios (Feign)
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(
            FeignException ex
    ) {

        String message = "Error en microservicio";

        try {

            if (
                    ex.contentUTF8() != null
                    &&
                    !ex.contentUTF8().isBlank()
            ) {

                message = ex.contentUTF8();
            }

        } catch (Exception ignored) {

            message = "Error en microservicio";
        }

        int status = (
                ex.status() > 0
                        ? ex.status()
                        : 502
        );

        return ResponseEntity
                .status(status)
                .body(message);
    }

    /**
     * Fallback general
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(
            Exception ex
    ) {

        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}