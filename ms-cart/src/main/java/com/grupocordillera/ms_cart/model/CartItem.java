package com.grupocordillera.ms_cart.model;

import lombok.Data;

@Data
public class CartItem {

    private String productCode;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private double subtotal;
}