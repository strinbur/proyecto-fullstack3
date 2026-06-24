package com.grupocordillera.ms_cart.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {

    private String code;
    private String name;
    private double price;
    private int quantity;
    private String category;
}