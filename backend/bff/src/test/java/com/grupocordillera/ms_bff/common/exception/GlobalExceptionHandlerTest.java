package com.grupocordillera.ms_bff.common.exception;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    // ---------- handleValidation ----------

    @Test
    void handleValidation_devuelvePrimerCampoPrioritario() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "category", "Categoria invalida"));
        bindingResult.addError(new FieldError("target", "email", "Correo invalido"));

        Method method = this.getClass().getDeclaredMethod("handleValidation_devuelvePrimerCampoPrioritario");
        MethodParameter parameter = new MethodParameter(method, -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<?> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Correo invalido", response.getBody());
    }

    @Test
    void handleValidation_sinCampoPrioritario_usaPrimerErrorDisponible() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "otroCampo", "Error generico"));

        Method method = this.getClass().getDeclaredMethod("handleValidation_sinCampoPrioritario_usaPrimerErrorDisponible");
        MethodParameter parameter = new MethodParameter(method, -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<?> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error generico", response.getBody());
    }

    @Test
    void handleValidation_sinErrores_usaMensajePorDefecto() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");

        Method method = this.getClass().getDeclaredMethod("handleValidation_sinErrores_usaMensajePorDefecto");
        MethodParameter parameter = new MethodParameter(method, -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<?> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de validación", response.getBody());
    }

    // ---------- handleRuntime ----------

    @Test
    void handleRuntime_devuelveMensajeDeLaExcepcion() {
        RuntimeException ex = new RuntimeException("Error de negocio");

        ResponseEntity<?> response = handler.handleRuntime(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de negocio", response.getBody());
    }

    // ---------- handleFeignException ----------

    @Test
    void handleFeignException_conContenidoYStatus_devuelveContenidoYStatusOriginal() {
        Request request = Request.create(
                Request.HttpMethod.GET,
                "/test",
                Collections.emptyMap(),
                null,
                new RequestTemplate()
        );

        FeignException ex = FeignException.errorStatus(
                "getCart",
                feign.Response.builder()
                        .status(404)
                        .reason("Not Found")
                        .request(request)
                        .headers(Collections.emptyMap())
                        .body("Carrito no encontrado".getBytes(StandardCharsets.UTF_8))
                        .build()
        );

        ResponseEntity<?> response = handler.handleFeignException(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Carrito no encontrado", response.getBody());
    }

    @Test
    void handleFeignException_sinContenido_usaMensajePorDefecto() {
        Request request = Request.create(
                Request.HttpMethod.GET,
                "/test",
                Collections.emptyMap(),
                null,
                new RequestTemplate()
        );

        FeignException ex = FeignException.errorStatus(
                "getCart",
                feign.Response.builder()
                        .status(500)
                        .reason("Internal Server Error")
                        .request(request)
                        .headers(Collections.emptyMap())
                        .body(new byte[0])
                        .build()
        );

        ResponseEntity<?> response = handler.handleFeignException(ex);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Error en microservicio", response.getBody());
    }

    @Test
    void handleFeignException_statusMenorOIgualCero_usa502() {
        FeignException ex = mock(FeignException.class);
        org.mockito.Mockito.when(ex.status()).thenReturn(-1);
        org.mockito.Mockito.when(ex.contentUTF8()).thenReturn("");

        ResponseEntity<?> response = handler.handleFeignException(ex);

        assertEquals(502, response.getStatusCode().value());
    }

    // ---------- handleGeneral ----------

    @Test
    void handleGeneral_devuelve500ConMensajeGenerico() {
        Exception ex = new Exception("Algo exploto");

        ResponseEntity<?> response = handler.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno del servidor", response.getBody());
    }
}