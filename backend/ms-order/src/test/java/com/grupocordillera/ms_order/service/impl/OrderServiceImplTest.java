package com.grupocordillera.ms_order.service.impl;

import com.grupocordillera.ms_order.cart.client.CartClient;
import com.grupocordillera.ms_order.cart.client.CartResponseDTO;
import com.grupocordillera.ms_order.common.exception.OrderException;
import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.inventory.client.InventoryClient;
import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.grupocordillera.ms_order.model.OrderStatus;
import com.grupocordillera.ms_order.cart.client.CartItemDTO;
import com.grupocordillera.ms_order.inventory.client.InventoryResponseDTO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartClient cartClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void shouldThrowExceptionWhenCartIsEmpty() {
        CartResponseDTO cart = new CartResponseDTO();
        cart.setItems(new ArrayList<>());

        when(cartClient.getCart()).thenReturn(cart);

        OrderException exception = assertThrows(
                OrderException.class,
                () -> service.createOrder(
                        "test@test.com",
                        "test"
                )
        );

        assertEquals("Carrito vacío", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        when(orderRepository.findById("123"))
                .thenReturn(java.util.Optional.empty());

        OrderException exception = assertThrows(
                OrderException.class,
                () -> service.getOrderById("123")
        );

        assertEquals("Orden no encontrada", exception.getMessage());
    }


    @Test
    void shouldReturnOrdersByUser() {
        Order order = new Order();
        order.setUserEmail("test@test.com");

        when(orderRepository.findByUserEmail("test@test.com"))
                .thenReturn(List.of(order));

        List<OrderResponseDTO> result = service.getOrdersByUser("test@test.com");

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnAllOrders() {
        Order order = new Order();

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        List<OrderResponseDTO> result = service.getAllOrders();

        assertEquals(1, result.size());
    }


    @Test
    void shouldCreateOrderSuccessfully() {

        CartItemDTO item = new CartItemDTO();
        item.setProductCode("P001");
        item.setName("Producto Test");
        item.setPrice(1000);
        item.setQuantity(2);
        item.setCategory("Tecnologia");
        item.setSubtotal(2000);

        CartResponseDTO cart = new CartResponseDTO();
        cart.setUserEmail("test@test.com");
        cart.setItems(List.of(item));
        cart.setTotal(2000);

        InventoryResponseDTO inventory = new InventoryResponseDTO();
        inventory.setCode("P001");
        inventory.setName("Producto Test");
        inventory.setPrice(1000);
        inventory.setQuantity(10);
        inventory.setCategory("Tecnologia");
        inventory.setBrand("Marca Test");

        Order savedOrder = new Order();
        savedOrder.setId("ORD-1");
        savedOrder.setUserEmail("test@test.com");
        savedOrder.setUserName("Patricio");
        savedOrder.setTotal(2000);

        when(cartClient.getCart()).thenReturn(cart);

        when(inventoryClient.getByCode("P001"))
                .thenReturn(inventory);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        OrderResponseDTO result =
                service.createOrder(
                        "test@test.com",
                        "Patricio"
                );

        assertNotNull(result);
        assertEquals("ORD-1", result.getId());
        assertEquals("test@test.com", result.getUserEmail());
    }

    
@Test
void shouldReturnOrdersByStatus() {
    Order order = new Order();
    order.setStatus(OrderStatus.PENDIENTE);

    when(orderRepository.findByStatus(OrderStatus.PENDIENTE)).thenReturn(List.of(order));

    List<OrderResponseDTO> result = service.getOrdersByStatus("PENDIENTE");

    assertEquals(1, result.size());
}

@Test
void shouldThrowExceptionWhenStatusIsInvalid() {
    OrderException exception = assertThrows(OrderException.class, 
            () -> service.getOrdersByStatus("estado-invalido"));

    assertEquals("Estado inválido: estado-invalido", exception.getMessage());
}

@Test
void shouldUpdateOrderStatusSuccessfully() {
    Order order = new Order();
    order.setId("ORD-1");
    order.setStatus(OrderStatus.PENDIENTE);

    when(orderRepository.findById("ORD-1")).thenReturn(java.util.Optional.of(order));
    when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

    OrderResponseDTO result = service.updateOrderStatus("ORD-1", "COMPLETADO");

    assertNotNull(result);
    assertEquals(OrderStatus.COMPLETADO, order.getStatus());
}

@Test
void shouldThrowExceptionWhenUpdatingNonExistingOrder() {
    when(orderRepository.findById("123")).thenReturn(java.util.Optional.empty());

    OrderException exception = assertThrows(OrderException.class, 
            () -> service.updateOrderStatus("123", "COMPLETADO"));

    assertEquals("Orden no encontrada", exception.getMessage());
}

}