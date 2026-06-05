package com.grupocordillera.ms_order.dto;

import com.grupocordillera.ms_order.model.OrderItem;
import com.grupocordillera.ms_order.model.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {

    private String id;
    private String userEmail;
    private String userName;
    private List<OrderItem> items;
    private double total;
    private OrderStatus status;
    private LocalDateTime createdAt;
}