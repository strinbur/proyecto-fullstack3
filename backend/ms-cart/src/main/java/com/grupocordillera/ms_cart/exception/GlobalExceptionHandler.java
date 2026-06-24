package com.grupocordillera.ms_cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Manejador global de excepciones para la API del carrito.
 *
 * Convierte excepciones en respuestas HTTP adecuadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones con estado HTTP explícito.
     *
     * @param ex excepción con código de estado
     * @return respuesta HTTP con el estado y el motivo de la excepción
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    /**
     * Maneja errores generales no controlados.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 500 con mensaje genérico de error interno
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}