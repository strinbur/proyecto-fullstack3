package com.grupocordillera.ms_order.dto;

import lombok.Data;

@Data
public class InventoryUpdateDTO {

    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;
}