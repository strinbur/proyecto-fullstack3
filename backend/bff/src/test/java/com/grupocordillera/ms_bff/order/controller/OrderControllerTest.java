package com.grupocordillera.ms_bff.order.controller;

import com.grupocordillera.ms_bff.order.dto.OrderResponseDTO;
import com.grupocordillera.ms_bff.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderControllerTest {

    private final OrderService service =
            Mockito.mock(OrderService.class);

    private final OrderController controller =
            new OrderController(service);

    @Test
    void shouldCreateOrder() {

        OrderResponseDTO dto =
                new OrderResponseDTO();

        Mockito.when(
                service.createOrder(
                        "test@test.cl"
                )
        ).thenReturn(dto);

        OrderResponseDTO response =
                controller.createOrder(
                        "test@test.cl"
                );

        assertNotNull(response);
    }

    @Test
    void shouldGetOrdersByUser() {

        List<OrderResponseDTO> orders =
                List.of(new OrderResponseDTO());

        Mockito.when(
                service.getOrdersByUser(
                        "test@test.cl"
                )
        ).thenReturn(orders);

        List<OrderResponseDTO> response =
                controller.getOrdersByUser(
                        "test@test.cl"
                );

        assertNotNull(response);
    }

    @Test
    void shouldGetOrderById() {

        OrderResponseDTO dto =
                new OrderResponseDTO();

        Mockito.when(service.getOrderById("1"))
                .thenReturn(dto);

        OrderResponseDTO response =
                controller.getOrderById("1");

        assertNotNull(response);
    }

    @Test
    void shouldGetAllOrders() {

        List<OrderResponseDTO> orders =
                List.of(new OrderResponseDTO());

        Mockito.when(service.getAllOrders())
                .thenReturn(orders);

        List<OrderResponseDTO> response =
                controller.getAllOrders();

        assertNotNull(response);
    }

    @Test
    void shouldGetOrdersByStatus() {

        List<OrderResponseDTO> orders =
                List.of(new OrderResponseDTO());

        Mockito.when(
                service.getOrdersByStatus(
                        "PENDING"
                )
        ).thenReturn(orders);

        List<OrderResponseDTO> response =
                controller.getOrdersByStatus(
                        "PENDING"
                );

        assertNotNull(response);
    }
}