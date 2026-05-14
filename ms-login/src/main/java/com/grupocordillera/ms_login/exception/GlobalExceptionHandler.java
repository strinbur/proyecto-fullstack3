package com.grupocordillera.ms_login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginException(LoginException ex) {

        String msg = ex.getMessage();

        if ("Usuario no encontrado".equals(msg)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }

        if ("Contraseña incorrecta".equals(msg)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
        }

        if ("El correo ya esta registrado".equals(msg)
                || "El correo ya esta en uso".equals(msg)
                || "Campos obligatorios vacíos".equals(msg)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Error de validación");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}