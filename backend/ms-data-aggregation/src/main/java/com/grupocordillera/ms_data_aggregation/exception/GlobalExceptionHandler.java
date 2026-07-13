package com.grupocordillera.ms_data_aggregation.exception;

import com.grupocordillera.ms_data_aggregation.glitchtip.GlitchTipErrorReporter;
import feign.FeignException;
import io.sentry.SentryLevel;
import org.springframework.dao.DataAccessException;
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

    /**
     * Validaciones DTO (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Error de validación");

        // No se manda a GlitchTip: error de input del cliente.
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Errores de negocio y errores de infraestructura (Mongo, etc.),
     * ambos llegan como RuntimeException.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {

        if (ex instanceof DataAccessException) {
            errorReporter.captureException(ex, "Error de acceso a datos en ms-data-aggregation");
        } else {
            errorReporter.captureMessage("Error de negocio en ms-data-aggregation: " + ex.getMessage(), SentryLevel.WARNING);
        }

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Errores desde otros microservicios (Feign).
     * Este handler es especialmente importante acá: si ms-data-aggregation
     * le pega a varios microservicios para armar su respuesta, este es el
     * punto donde vas a ver quién está fallando río abajo.
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

        if (status >= 500 || status == 502) {
            errorReporter.captureException(ex, "Fallo de microservicio downstream (status " + status + ") en ms-data-aggregation");
        } else {
            errorReporter.captureMessage("Microservicio downstream respondió " + status + ": " + message, SentryLevel.INFO);
        }

        return ResponseEntity.status(status).body(message);
    }

    /**
     * Fallback general
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        errorReporter.captureException(ex, "Excepcion no controlada en ms-data-aggregation");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}