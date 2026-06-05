package com.grupocordillera.ms_order.model;

import lombok.Data;

@Data
public class OrderItem {

    private String productCode;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private double subtotal;
}