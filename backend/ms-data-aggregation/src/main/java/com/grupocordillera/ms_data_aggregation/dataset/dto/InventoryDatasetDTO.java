package com.grupocordillera.ms_data_aggregation.dataset.dto;

import lombok.Data;

@Data
public class InventoryDatasetDTO {

    private String code;
    private String name;
    private int quantity;
    private double price;
    private String category;
}