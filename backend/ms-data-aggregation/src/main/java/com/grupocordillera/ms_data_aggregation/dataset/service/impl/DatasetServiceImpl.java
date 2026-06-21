package com.grupocordillera.ms_data_aggregation.dataset.service.impl;

import com.grupocordillera.ms_data_aggregation.dataset.client.InventoryClient;
import com.grupocordillera.ms_data_aggregation.dataset.client.OrderClient;
import com.grupocordillera.ms_data_aggregation.dataset.dto.CombinedDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.service.DatasetService;
import org.springframework.stereotype.Service;

@Service
public class DatasetServiceImpl implements DatasetService {

    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;

    public DatasetServiceImpl(
            OrderClient orderClient,
            InventoryClient inventoryClient
    ) {
        this.orderClient = orderClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public CombinedDatasetDTO getDataset() {

        CombinedDatasetDTO dataset = new CombinedDatasetDTO();

        dataset.setOrders(
                orderClient.getAllOrders()
        );

        dataset.setInventory(
                inventoryClient.getAllInventory()
        );

        return dataset;
    }
}