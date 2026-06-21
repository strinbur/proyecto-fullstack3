package com.grupocordillera.ms_bff.order.service;

import java.util.List;
import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(String userEmail);

    List<OrderResponseDTO> getOrdersByUser(String email);

    OrderResponseDTO getOrderById(String id);

    List<OrderResponseDTO> getAllOrders();

    List<OrderResponseDTO> getOrdersByStatus(String status);
}