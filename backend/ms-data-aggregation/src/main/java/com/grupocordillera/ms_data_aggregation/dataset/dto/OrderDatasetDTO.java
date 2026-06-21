package com.grupocordillera.ms_data_aggregation.dataset.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDatasetDTO {

    private String id;
    private String userEmail;
    private String userName;

    private List<OrderItemDatasetDTO> items;

    private double total;
    private String status;
    private LocalDateTime createdAt;
}