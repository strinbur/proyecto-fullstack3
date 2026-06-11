package com.grupocordillera.ms_bff.order.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private String productCode;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private double subtotal;
}