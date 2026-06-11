package com.grupocordillera.ms_bff.order.dto;

import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    private String id;
    private String userEmail;
    private String userName;
    private List<OrderItemDTO> items;
    private double total;
    private String status;
    private LocalDateTime createdAt;
}