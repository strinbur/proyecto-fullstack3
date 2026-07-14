package com.grupocordillera.ms_cart.exception;

import com.grupocordillera.ms_cart.glitchtip.GlitchTipErrorReporter;
import io.sentry.SentryLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final GlitchTipErrorReporter errorReporter;

    public GlobalExceptionHandler(GlitchTipErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {

        int status = ex.getStatusCode().value();

        if (status >= 500) {

            errorReporter.captureException(ex, "ResponseStatusException con status 5xx en ms-cart");
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            errorReporter.captureMessage("Recurso no encontrado: " + ex.getReason(), SentryLevel.INFO);
        } else if (status == HttpStatus.UNAUTHORIZED.value() || status == HttpStatus.FORBIDDEN.value()) {
            errorReporter.captureMessage("Acceso denegado: " + ex.getReason(), SentryLevel.WARNING);
        } else {
            errorReporter.captureMessage("Error de negocio (" + status + "): " + ex.getReason(), SentryLevel.INFO);
        }

        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        errorReporter.captureException(ex, "Excepcion no controlada en ms-cart");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}