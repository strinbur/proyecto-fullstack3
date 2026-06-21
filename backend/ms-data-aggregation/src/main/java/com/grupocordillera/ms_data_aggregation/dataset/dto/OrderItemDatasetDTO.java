package com.grupocordillera.ms_data_aggregation.dataset.dto;

import lombok.Data;

@Data
public class OrderItemDatasetDTO {

    private String productCode;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private double subtotal;
}