package com.grupocordillera.ms_inventory.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(MethodArgumentNotValidException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        respuesta.put("status", 400);
        respuesta.put("errores", errores);

        return ResponseEntity.badRequest().body(respuesta);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarErrores(ResponseStatusException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", ex.getStatusCode().value());
        respuesta.put("errores", List.of(ex.getReason()));

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(respuesta);
    }
}