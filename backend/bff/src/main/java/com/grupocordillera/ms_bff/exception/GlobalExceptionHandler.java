package com.grupocordillera.ms_bff.exception;

import com.grupocordillera.ms_bff.glitchtip.GlitchTipErrorReporter;
import feign.FeignException;
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

    /**
     * Validaciones del frontend
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        String[] order = {
                "name", "lastName", "email", "password",
                "code", "brand", "price", "quantity", "category"
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

        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Errores de negocio del BFF
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        errorReporter.captureMessage("Error de negocio en ms-bff: " + ex.getMessage(), SentryLevel.WARNING);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Errores provenientes de microservicios (Feign).
     * Este es el punto más importante del BFF: acá se ve si algún
     * microservicio detrás está fallando.
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException ex) {

        String message = "Error en microservicio";

        try {
            if (ex.contentUTF8() != null && !ex.contentUTF8().isBlank()) {
                message = ex.contentUTF8();
            }
        } catch (Exception ignored) {
            message = "Error en microservicio";
        }

        int status = ex.status() > 0 ? ex.status() : 502;

        if (status >= 500 || status == 502) {
            errorReporter.captureException(ex, "Fallo de microservicio downstream (status " + status + ") en ms-bff");
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
        errorReporter.captureException(ex, "Excepcion no controlada en ms-bff");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}