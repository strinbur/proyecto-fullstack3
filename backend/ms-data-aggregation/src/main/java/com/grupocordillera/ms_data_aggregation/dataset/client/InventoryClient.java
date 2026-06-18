package com.grupocordillera.ms_data_aggregation.dataset.client;

import com.grupocordillera.ms_data_aggregation.dataset.dto.InventoryDatasetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
    name = "ms-inventory",
    url = "${inventory.url}",
    path = "/inventory"
)
public interface InventoryClient {

    @GetMapping
    List<InventoryDatasetDTO> getAllInventory();
}