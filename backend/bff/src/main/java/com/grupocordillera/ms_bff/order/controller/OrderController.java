package com.grupocordillera.ms_bff.order.controller;

import com.grupocordillera.ms_bff.order.dto.OrderItemDTO;
import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;
import com.grupocordillera.ms_bff.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bff/orders")
@CrossOrigin
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponseDTO createOrder(@RequestHeader String userEmail) {
        log.info("BFF CREATE ORDER - {}", userEmail);
        return service.createOrder(userEmail);
    }

    @GetMapping("/user/{email}")
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable String email) {
        log.info("BFF GET ORDERS USER - {}", email);
        return service.getOrdersByUser(email);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable String id) {
        log.info("BFF GET ORDER - {}", id);
        return service.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        log.info("BFF GET ALL ORDERS");
        return service.getAllOrders();
    }

    @GetMapping("/status/{status}")
    public List<OrderResponseDTO> getOrdersByStatus(@PathVariable String status) {
        log.info("BFF GET ORDERS BY STATUS: {}", status);
        return service.getOrdersByStatus(status);
    }
}