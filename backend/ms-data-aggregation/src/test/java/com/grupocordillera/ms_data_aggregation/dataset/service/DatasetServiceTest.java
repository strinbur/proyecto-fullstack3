package com.grupocordillera.ms_data_aggregation.dataset.service;

import com.grupocordillera.ms_data_aggregation.dataset.client.InventoryClient;
import com.grupocordillera.ms_data_aggregation.dataset.client.OrderClient;
import com.grupocordillera.ms_data_aggregation.dataset.dto.CombinedDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.service.impl.DatasetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatasetServiceTest {

    @Mock
    private OrderClient orderClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private DatasetServiceImpl service;

    @Test
    void getDataset_shouldReturnData() {

        when(orderClient.getAllOrders()).thenReturn(List.of());
        when(inventoryClient.getAllInventory()).thenReturn(List.of());

        CombinedDatasetDTO result = service.getDataset();

        assertNotNull(result);

        verify(orderClient, times(1)).getAllOrders();
        verify(inventoryClient, times(1)).getAllInventory();
    }
}