package com.grupocordillera.ms_order.cart.client;

import lombok.Data;

@Data
public class CartItemDTO {

    private String productCode;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private double subtotal;
}