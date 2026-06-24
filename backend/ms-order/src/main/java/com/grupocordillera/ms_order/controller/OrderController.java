package com.grupocordillera.ms_order.controller;

import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponseDTO createOrder() {
        return service.createOrder();
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/user/{email}")
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable String email) {
        return service.getOrdersByUser(email);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable String id) {
        return service.getOrderById(id);
    }

    @GetMapping("/status/{status}")
    public List<OrderResponseDTO> getOrdersByStatus(@PathVariable String status) {
        return service.getOrdersByStatus(status);
    }

    @PutMapping("/{id}/status/{status}")
    public OrderResponseDTO updateOrderStatus(@PathVariable String id, @PathVariable String status) {
        return service.updateOrderStatus(id, status);
    }

}