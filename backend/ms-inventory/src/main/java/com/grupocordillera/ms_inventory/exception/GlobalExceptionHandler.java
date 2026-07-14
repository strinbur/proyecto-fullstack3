package com.grupocordillera.ms_inventory.exception;

import com.grupocordillera.ms_inventory.glitchtip.GlitchTipErrorReporter;
import io.sentry.SentryLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final GlitchTipErrorReporter errorReporter;

    public GlobalExceptionHandler(GlitchTipErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

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

        int status = ex.getStatusCode().value();
        response.put("status", status);
        response.put("errores", List.of(ex.getReason()));

        if (status >= 500) {
            errorReporter.captureException(ex, "ResponseStatusException con status 5xx en ms-inventory");
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            errorReporter.captureMessage("Recurso no encontrado: " + ex.getReason(), SentryLevel.INFO);
        } else if (status == HttpStatus.UNAUTHORIZED.value() || status == HttpStatus.FORBIDDEN.value()) {
            errorReporter.captureMessage("Acceso denegado: " + ex.getReason(), SentryLevel.WARNING);
        } else {
            // Otros 4xx (409 conflicto de stock, etc.)
            errorReporter.captureMessage("Error de negocio (" + status + "): " + ex.getReason(), SentryLevel.INFO);
        }

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            Exception ex
    ) {
        errorReporter.captureException(ex, "Excepcion no controlada en ms-inventory");

        Map<String, Object> response = new HashMap<>();
        response.put("status", 500);
        response.put("errores", List.of("Error interno del servidor"));

        return ResponseEntity
                .internalServerError()
                .body(response);
    }
}