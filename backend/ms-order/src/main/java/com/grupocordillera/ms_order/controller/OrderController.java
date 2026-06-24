package com.grupocordillera.ms_order.controller;

import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar órdenes de compra.
 *
 * Expone endpoints para crear órdenes, consultar órdenes por usuario, estado o identificador,
 * y actualizar el estado de una orden.
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Crea una nueva orden para el usuario autenticado.
     *
     * @return orden creada
     */
    @PostMapping
    public OrderResponseDTO createOrder() {
        return service.createOrder();
    }

    /**
     * Recupera todas las órdenes.
     *
     * @return lista de órdenes
     */
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return service.getAllOrders();
    }

    /**
     * Recupera las órdenes del usuario identificado por correo electrónico.
     *
     * @param email correo electrónico del usuario
     * @return lista de órdenes del usuario
     */
    @GetMapping("/user/{email}")
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable String email) {
        return service.getOrdersByUser(email);
    }

    /**
     * Recupera una orden por su identificador.
     *
     * @param id identificador de la orden
     * @return orden encontrada
     */
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable String id) {
        return service.getOrderById(id);
    }

    /**
     * Recupera las órdenes que se encuentran en un estado específico.
     *
     * @param status nombre del estado de la orden
     * @return lista de órdenes con el estado indicado
     */
    @GetMapping("/status/{status}")
    public List<OrderResponseDTO> getOrdersByStatus(@PathVariable String status) {
        return service.getOrdersByStatus(status);
    }

    /**
     * Actualiza el estado de una orden existente.
     *
     * @param id     identificador de la orden
     * @param status nuevo estado de la orden
     * @return orden actualizada
     */
    @PutMapping("/{id}/status/{status}")
    public OrderResponseDTO updateOrderStatus(@PathVariable String id, @PathVariable String status) {
        return service.updateOrderStatus(id, status);
    }

} 