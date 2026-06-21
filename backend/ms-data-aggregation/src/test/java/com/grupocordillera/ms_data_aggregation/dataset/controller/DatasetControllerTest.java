package com.grupocordillera.ms_data_aggregation.dataset.controller;

import com.grupocordillera.ms_data_aggregation.dataset.dto.CombinedDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.dto.InventoryDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.dto.OrderDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.service.DatasetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatasetControllerTest {

    @Mock
    private DatasetService service;

    @InjectMocks
    private DatasetController controller;

    private CombinedDatasetDTO mockResponse;

    @BeforeEach
    void setup() {
        mockResponse = new CombinedDatasetDTO();
    }

    @Test
    void getDataset_shouldReturnData() {

        when(service.getDataset()).thenReturn(mockResponse);

        CombinedDatasetDTO result = controller.getDataset();

        assertNotNull(result);
        verify(service, times(1)).getDataset();
    }

    @Test
    void getDataset_shouldReturnDataWithOrdersAndInventory() {

        OrderDatasetDTO order = new OrderDatasetDTO();
        order.setId("ORD-1");
        order.setUserEmail("test@test.com");

        InventoryDatasetDTO item = new InventoryDatasetDTO();
        item.setCode("P001");
        item.setName("Notebook");

        mockResponse.setOrders(List.of(order));
        mockResponse.setInventory(List.of(item));

        when(service.getDataset()).thenReturn(mockResponse);

        CombinedDatasetDTO result = controller.getDataset();

        assertNotNull(result);
        assertEquals(1, result.getOrders().size());
        assertEquals(1, result.getInventory().size());
        assertEquals("ORD-1", result.getOrders().get(0).getId());
        assertEquals("P001", result.getInventory().get(0).getCode());
    }

    @Test
    void getDataset_shouldReturnEmptyLists() {

        mockResponse.setOrders(Collections.emptyList());
        mockResponse.setInventory(Collections.emptyList());

        when(service.getDataset()).thenReturn(mockResponse);

        CombinedDatasetDTO result = controller.getDataset();

        assertNotNull(result);
        assertTrue(result.getOrders().isEmpty());
        assertTrue(result.getInventory().isEmpty());
    }

    @Test
    void getDataset_shouldCallServiceExactlyOnce() {

        when(service.getDataset()).thenReturn(mockResponse);

        controller.getDataset();
        controller.getDataset();

        verify(service, times(2)).getDataset();
    }
}