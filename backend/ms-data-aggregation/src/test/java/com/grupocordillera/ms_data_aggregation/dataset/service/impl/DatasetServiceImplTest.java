package com.grupocordillera.ms_data_aggregation.dataset.service.impl;

import com.grupocordillera.ms_data_aggregation.dataset.client.InventoryClient;
import com.grupocordillera.ms_data_aggregation.dataset.client.OrderClient;
import com.grupocordillera.ms_data_aggregation.dataset.dto.CombinedDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.dto.InventoryDatasetDTO;
import com.grupocordillera.ms_data_aggregation.dataset.dto.OrderDatasetDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatasetServiceImplTest {

    @Mock
    private OrderClient orderClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private DatasetServiceImpl service;

    @Test
    void shouldReturnCombinedDatasetWithOrdersAndInventory() {

        OrderDatasetDTO order = new OrderDatasetDTO();
        order.setId("ORD-1");
        order.setUserEmail("test@test.com");
        order.setTotal(2000.0);
        order.setStatus("PAID");

        InventoryDatasetDTO inventoryItem = new InventoryDatasetDTO();
        inventoryItem.setCode("P001");
        inventoryItem.setName("Notebook");
        inventoryItem.setQuantity(15);
        inventoryItem.setPrice(2000.0);
        inventoryItem.setCategory("Tech");

        when(orderClient.getAllOrders()).thenReturn(List.of(order));
        when(inventoryClient.getAllInventory()).thenReturn(List.of(inventoryItem));

        CombinedDatasetDTO result = service.getDataset();

        assertNotNull(result);
        assertEquals(1, result.getOrders().size());
        assertEquals(1, result.getInventory().size());
        assertEquals("ORD-1", result.getOrders().get(0).getId());
        assertEquals("P001", result.getInventory().get(0).getCode());

        verify(orderClient).getAllOrders();
        verify(inventoryClient).getAllInventory();
    }

    @Test
    void shouldReturnEmptyDatasetWhenNoOrdersOrInventory() {

        when(orderClient.getAllOrders()).thenReturn(Collections.emptyList());
        when(inventoryClient.getAllInventory()).thenReturn(Collections.emptyList());

        CombinedDatasetDTO result = service.getDataset();

        assertNotNull(result);
        assertTrue(result.getOrders().isEmpty());
        assertTrue(result.getInventory().isEmpty());
    }

    @Test
    void shouldReturnDatasetWithMultipleOrdersAndInventoryItems() {

        OrderDatasetDTO order1 = new OrderDatasetDTO();
        order1.setId("ORD-1");

        OrderDatasetDTO order2 = new OrderDatasetDTO();
        order2.setId("ORD-2");

        InventoryDatasetDTO item1 = new InventoryDatasetDTO();
        item1.setCode("P001");

        InventoryDatasetDTO item2 = new InventoryDatasetDTO();
        item2.setCode("P002");

        when(orderClient.getAllOrders()).thenReturn(List.of(order1, order2));
        when(inventoryClient.getAllInventory()).thenReturn(List.of(item1, item2));

        CombinedDatasetDTO result = service.getDataset();

        assertEquals(2, result.getOrders().size());
        assertEquals(2, result.getInventory().size());
    }

    @Test
    void shouldOnlyHaveOrdersWhenInventoryIsEmpty() {

        OrderDatasetDTO order = new OrderDatasetDTO();
        order.setId("ORD-1");

        when(orderClient.getAllOrders()).thenReturn(List.of(order));
        when(inventoryClient.getAllInventory()).thenReturn(Collections.emptyList());

        CombinedDatasetDTO result = service.getDataset();

        assertEquals(1, result.getOrders().size());
        assertTrue(result.getInventory().isEmpty());
    }
}