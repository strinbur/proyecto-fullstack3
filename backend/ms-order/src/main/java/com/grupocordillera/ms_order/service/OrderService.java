package com.grupocordillera.ms_order.service;

import com.grupocordillera.ms_order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder();

    List<OrderResponseDTO> getOrdersByUser(String userEmail);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(String id);

    List<OrderResponseDTO> getOrdersByStatus(String status);

    OrderResponseDTO updateOrderStatus(String id, String status);
}