package com.grupocordillera.ms_bff.order.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;

@FeignClient(name = "ms-order", path = "/orders", configuration = com.grupocordillera.ms_bff.common.config.FeignConfig.class)
public interface OrderClient {

    @PostMapping
    OrderResponseDTO createOrder(@RequestHeader("userEmail") String userEmail);

    @GetMapping("/user/{email}")
    List<OrderResponseDTO> getOrdersByUser(@PathVariable("email") String email);

    @GetMapping("/{id}")
    OrderResponseDTO getOrderById(@PathVariable("id") String id);

    @GetMapping
    List<OrderResponseDTO> getAllOrders();

    @GetMapping("/status/{status}")
    List<OrderResponseDTO> getOrdersByStatus(@PathVariable("status") String status);
}