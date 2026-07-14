package com.grupocordillera.ms_login.exception;

import com.grupocordillera.ms_login.glitchtip.GlitchTipErrorReporter;
import io.sentry.SentryLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final GlitchTipErrorReporter errorReporter;

    public GlobalExceptionHandler(GlitchTipErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginException(LoginException ex) {

        String msg = ex.getMessage();

        if ("Usuario no encontrado".equals(msg)) {
            errorReporter.captureMessage("Login fallido: usuario no encontrado", SentryLevel.INFO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }

        if ("Contraseña incorrecta".equals(msg)) {
            errorReporter.captureMessage("Login fallido: contraseña incorrecta", SentryLevel.WARNING);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
        }

        if ("El correo ya esta registrado".equals(msg)
                || "El correo ya esta en uso".equals(msg)
                || "Campos obligatorios vacíos".equals(msg)) {
            errorReporter.captureMessage("Registro rechazado: " + msg, SentryLevel.INFO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }

        errorReporter.captureMessage("LoginException no clasificada: " + msg, SentryLevel.WARNING);
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
        errorReporter.captureException(ex, "RuntimeException no controlada explícitamente en ms-login");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        errorReporter.captureException(ex, "Excepcion no controlada en ms-login");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}