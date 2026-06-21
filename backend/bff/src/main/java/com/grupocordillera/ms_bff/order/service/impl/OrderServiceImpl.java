package com.grupocordillera.ms_bff.order.service.impl;

import com.grupocordillera.ms_bff.order.client.OrderClient;
import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;
import com.grupocordillera.ms_bff.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderClient client;

    public OrderServiceImpl(OrderClient client) {
        this.client = client;
    }

    @Override
    public OrderResponseDTO createOrder(String userEmail) {
        return client.createOrder(userEmail);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(String email) {
        return client.getOrdersByUser(email);
    }

    @Override
    public OrderResponseDTO getOrderById(String id) {
        return client.getOrderById(id);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return client.getAllOrders();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        return client.getOrdersByStatus(status);
    }
}