package com.grupocordillera.ms_order.controller;

import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.model.OrderStatus;
import com.grupocordillera.ms_order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void shouldCreateOrderSuccessfully() {
        String email = "test@gmail.com";

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId("1");
        response.setUserEmail(email);
        response.setUserName("test user");
        response.setTotal(1000);
        response.setStatus(OrderStatus.PENDIENTE);
        response.setCreatedAt(LocalDateTime.now());

        when(orderService.createOrder(email, email)).thenReturn(response);

        OrderResponseDTO result = orderController.createOrder(email);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(email, result.getUserEmail());

        verify(orderService, times(1)).createOrder(email, email);
    }

    @Test
    void shouldGetOrdersByUser() {
        String email = "user@gmail.com";

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setUserEmail(email);

        when(orderService.getOrdersByUser(email)).thenReturn(List.of(dto));

        List<OrderResponseDTO> result = orderController.getOrdersByUser(email);

        assertEquals(1, result.size());
        assertEquals(email, result.get(0).getUserEmail());

        verify(orderService).getOrdersByUser(email);
    }

    @Test
    void shouldGetOrderById() {
        String id = "123";

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(id);

        when(orderService.getOrderById(id)).thenReturn(dto);

        OrderResponseDTO result = orderController.getOrderById(id);

        assertEquals(id, result.getId());

        verify(orderService).getOrderById(id);
    }

    @Test
    void shouldGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(
                new OrderResponseDTO(),
                new OrderResponseDTO()
        ));

        List<OrderResponseDTO> result = orderController.getAllOrders();

        assertEquals(2, result.size());

        verify(orderService).getAllOrders();
    }

    @Test
    void shouldGetOrdersByStatus() {
        String status = "pendiente";

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setStatus(OrderStatus.PENDIENTE);

        when(orderService.getOrdersByStatus(status)).thenReturn(List.of(dto));

        List<OrderResponseDTO> result = orderController.getOrdersByStatus(status);

        assertEquals(1, result.size());
        assertEquals(OrderStatus.PENDIENTE, result.get(0).getStatus());

        verify(orderService).getOrdersByStatus(status);
    }
}