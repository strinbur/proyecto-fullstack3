package com.grupocordillera.ms_bff.inventory.dto;

import lombok.Data;

import java.util.Map;

@Data
public class InventoryResponseDTO {

    private String id;
    private String code;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;

    private Map<String, Object> specs;
}