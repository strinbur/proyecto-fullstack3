package com.grupocordillera.ms_order.service;

import com.grupocordillera.ms_order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(String userEmail, String userName);

    List<OrderResponseDTO> getOrdersByUser(String userEmail);

    OrderResponseDTO getOrderById(String id);

    List<OrderResponseDTO> getAllOrders();

    List<OrderResponseDTO> getOrdersByStatus(String status);
}