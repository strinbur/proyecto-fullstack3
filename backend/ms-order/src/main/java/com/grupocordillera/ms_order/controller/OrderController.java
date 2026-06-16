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
    public OrderResponseDTO createOrder(@RequestHeader String userEmail) {
        log.info("CREATE ORDER - email: {}", userEmail);
        return service.createOrder(userEmail, userEmail);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        log.info("GET ALL ORDERS");
        return service.getAllOrders();
    }

    @GetMapping("/user/{email}")
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable String email) {
        log.info("GET ORDERS BY USER - email: {}", email);
        return service.getOrdersByUser(email);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable String id) {
        log.info("GET ORDER BY ID - id: {}", id);
        return service.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        log.info("GET ALL ORDERS");
        return service.getAllOrders();
    }

    @GetMapping("/status/{status}")
    public List<OrderResponseDTO> getOrdersByStatus(
            @PathVariable String status
    ) {

        log.info("GET ORDERS BY STATUS - {}", status);
        return service.getOrdersByStatus(status);
    }

    @PutMapping("/{id}/status/{status}")
    public OrderResponseDTO updateOrderStatus(@PathVariable String id, @PathVariable String status) {
        log.info("UPDATE ORDER STATUS - id: {} status: {}", id, status);
        return service.updateOrderStatus(id, status);
    }
}