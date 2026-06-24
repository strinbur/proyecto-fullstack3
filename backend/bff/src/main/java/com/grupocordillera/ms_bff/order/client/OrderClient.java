package com.grupocordillera.ms_bff.order.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;

/**
 * Cliente Feign para comunicarse con el microservicio `ms-order`.
 */
@FeignClient(name = "ms-order", path = "/orders", configuration = com.grupocordillera.ms_bff.config.FeignConfig.class)
public interface OrderClient {

    /**
     * Crea una nueva orden para el `userEmail` provisto.
     */
    @PostMapping
    OrderResponseDTO createOrder(@RequestHeader("userEmail") String userEmail);

    /**
     * Recupera las órdenes realizadas por el usuario identificado por email.
     */
    @GetMapping("/user/{email}")
    List<OrderResponseDTO> getOrdersByUser(@PathVariable("email") String email);

    /**
     * Recupera una orden por su identificador.
     */
    @GetMapping("/{id}")
    OrderResponseDTO getOrderById(@PathVariable("id") String id);

    /**
     * Recupera todas las órdenes (uso administrativo).
     */
    @GetMapping
    List<OrderResponseDTO> getAllOrders();

    /**
     * Recupera órdenes filtradas por estado.
     */
    @GetMapping("/status/{status}")
    List<OrderResponseDTO> getOrdersByStatus(@PathVariable("status") String status);
}