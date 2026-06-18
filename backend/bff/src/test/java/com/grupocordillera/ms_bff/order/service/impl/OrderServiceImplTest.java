package com.grupocordillera.ms_bff.order.service.impl;

import com.grupocordillera.ms_bff.order.client.OrderClient;
import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderServiceImplTest {

    private final OrderClient client =
            Mockito.mock(OrderClient.class);

    private final OrderServiceImpl service =
            new OrderServiceImpl(client);

    @Test
    void shouldCreateOrder() {

        OrderResponseDTO dto =
                new OrderResponseDTO();

        Mockito.when(client.createOrder("test@test.cl"))
                .thenReturn(dto);

        OrderResponseDTO response =
                service.createOrder("test@test.cl");

        assertNotNull(response);
    }

    @Test
    void shouldGetOrdersByUser() {

        List<OrderResponseDTO> orders =
                List.of(new OrderResponseDTO());

        Mockito.when(client.getOrdersByUser("test@test.cl"))
                .thenReturn(orders);

        List<OrderResponseDTO> response =
                service.getOrdersByUser("test@test.cl");

        assertNotNull(response);
    }

    @Test
    void shouldGetOrderById() {

        OrderResponseDTO dto =
                new OrderResponseDTO();

        Mockito.when(client.getOrderById("1"))
                .thenReturn(dto);

        OrderResponseDTO response =
                service.getOrderById("1");

        assertNotNull(response);
    }

    @Test
    void shouldGetAllOrders() {

        List<OrderResponseDTO> orders =
                List.of(new OrderResponseDTO());

        Mockito.when(client.getAllOrders())
                .thenReturn(orders);

        List<OrderResponseDTO> response =
                service.getAllOrders();

        assertNotNull(response);
    }

    @Test
    void shouldGetOrdersByStatus() {

        List<OrderResponseDTO> orders =
                List.of(new OrderResponseDTO());

        Mockito.when(client.getOrdersByStatus("PENDING"))
                .thenReturn(orders);

        List<OrderResponseDTO> response =
                service.getOrdersByStatus("PENDING");

        assertNotNull(response);
    }
}