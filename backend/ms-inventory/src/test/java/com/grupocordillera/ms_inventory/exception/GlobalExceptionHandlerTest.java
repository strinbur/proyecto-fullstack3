package com.grupocordillera.ms_inventory.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import com.grupocordillera.ms_inventory.exception.GlobalExceptionHandler;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void shouldHandleResponseStatusException() {

        ResponseStatusException ex =
                new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                );

        ResponseEntity<Map<String,Object>> response =
                handler.handleResponseStatusException(ex);

        assertEquals(
                404,
                response.getStatusCode().value()
        );
    }

    @Test
    void shouldHandleGeneralException() {

        Exception ex =
                new Exception("Error");

        ResponseEntity<Map<String,Object>> response =
                handler.handleGeneralException(ex);

        assertEquals(
                500,
                response.getStatusCode().value()
        );
    }
}