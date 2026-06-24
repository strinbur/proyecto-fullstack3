package com.grupocordillera.ms_order.service;

import com.grupocordillera.ms_order.dto.OrderResponseDTO;

import java.util.List;

/**
 * Servicio de órdenes para la lógica de negocio de creación y consulta de pedidos.
 */
public interface OrderService {

    /**
     * Crea una nueva orden para el usuario autenticado.
     *
     * @return orden creada
     */
    OrderResponseDTO createOrder();

    /**
     * Recupera las órdenes de un usuario por su correo electrónico.
     *
     * @param userEmail correo electrónico del usuario
     * @return lista de órdenes del usuario
     */
    List<OrderResponseDTO> getOrdersByUser(String userEmail);

    /**
     * Recupera todas las órdenes almacenadas.
     *
     * @return lista de órdenes
     */
    List<OrderResponseDTO> getAllOrders();

    /**
     * Recupera una orden por su identificador.
     *
     * @param id identificador de la orden
     * @return orden encontrada
     */
    OrderResponseDTO getOrderById(String id);

    /**
     * Recupera las órdenes según su estado.
     *
     * @param status estado de la orden
     * @return lista de órdenes con ese estado
     */
    List<OrderResponseDTO> getOrdersByStatus(String status);

    /**
     * Actualiza el estado de una orden existente.
     *
     * @param id     identificador de la orden
     * @param status nuevo estado de la orden
     * @return orden actualizada
     */
    OrderResponseDTO updateOrderStatus(String id, String status);
} 