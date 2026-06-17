package com.grupocordillera.ms_bff.common.exception;

import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void shouldHandleRuntimeException() {

        RuntimeException ex =
                new RuntimeException("Error runtime");

        ResponseEntity<?> response =
                handler.handleRuntime(ex);

        assertEquals(
                400,
                response.getStatusCode().value()
        );

        assertEquals(
                "Error runtime",
                response.getBody()
        );
    }

    @Test
    void shouldHandleGeneralException() {

        Exception ex =
                new Exception("Error");

        ResponseEntity<?> response =
                handler.handleGeneral(ex);

        assertEquals(
                500,
                response.getStatusCode().value()
        );

        assertEquals(
                "Error interno del servidor",
                response.getBody()
        );
    }

    @Test
    void shouldHandleFeignException() {

        FeignException ex =
                Mockito.mock(FeignException.class);

        Mockito.when(ex.status())
                .thenReturn(404);

        Mockito.when(ex.contentUTF8())
                .thenReturn("No encontrado");

        ResponseEntity<?> response =
                handler.handleFeignException(ex);

        assertEquals(
                404,
                response.getStatusCode().value()
        );

        assertEquals(
                "No encontrado",
                response.getBody()
        );
    }
}