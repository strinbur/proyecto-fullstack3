package com.grupocordillera.ms_bff.login.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // VALIDACIONES DEL FRONT
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        String[] order = {"nombre", "apellido", "correo", "password"};

        String error = null;

        for (String field : order) {

            error = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .filter(e -> e.getField().equals(field))
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse(null);

            if (error != null) break;
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

    //ERRORES DE NEGOCIO DEL BFF
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    // CLAVE: ERRORES QUE VIENEN DE MICROSERVICIOS (FEIGN)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeign(FeignException ex) {

        String message;

        try {
            message = ex.contentUTF8(); // 👈 mensaje real del ms-login
        } catch (Exception e) {
            message = "Error en microservicio";
        }

        return ResponseEntity
                .status(ex.status() > 0 ? ex.status() : 400)
                .body(message);
    }

    // FALLBACK GENERAL
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}