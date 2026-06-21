package com.grupocordillera.ms_data_aggregation.dataset.dto;

import lombok.Data;

import java.util.List;

@Data
public class CombinedDatasetDTO {

    private List<OrderDatasetDTO> orders;

    private List<InventoryDatasetDTO> inventory;
}