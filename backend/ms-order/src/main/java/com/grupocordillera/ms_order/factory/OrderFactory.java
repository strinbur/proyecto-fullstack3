package com.grupocordillera.ms_order.factory;

import com.grupocordillera.ms_order.dto.CartItemDTO;
import com.grupocordillera.ms_order.dto.CartResponseDTO;
import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.model.OrderItem;
import com.grupocordillera.ms_order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {

    public static Order createOrder(String userEmail, String userName, CartResponseDTO cart) {
        Order order = new Order();
        
        order.setUserEmail(userEmail);
        order.setUserName(userName);
        order.setItems(mapItems(cart.getItems()));
        order.setTotal(cart.getTotal());
        order.setStatus(OrderStatus.PENDIENTE);
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }

    private static List<OrderItem> mapItems(List<CartItemDTO> items) {
        return items.stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            
            orderItem.setProductCode(item.getProductCode());
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setCategory(item.getCategory());
            orderItem.setSubtotal(item.getSubtotal());
            
            return orderItem;
        }).toList();
    }

    public static OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        
        dto.setId(order.getId());
        dto.setUserEmail(order.getUserEmail());
        dto.setUserName(order.getUserName());
        dto.setItems(order.getItems());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        return dto;
    }
}