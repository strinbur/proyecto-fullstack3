package com.grupocordillera.ms_order.factory;

import com.grupocordillera.ms_order.cart.client.CartItemDTO;
import com.grupocordillera.ms_order.cart.client.CartResponseDTO;
import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.model.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {

    @Test
    void shouldCreateOrder() {

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

        Order order = OrderFactory.createOrder(
                "test@test.com",
                "Patricio",
                cart
        );

        assertNotNull(order);

        assertEquals("test@test.com", order.getUserEmail());
        assertEquals("Patricio", order.getUserName());
        assertEquals(2000, order.getTotal());
        assertEquals(OrderStatus.PENDIENTE, order.getStatus());

        assertNotNull(order.getCreatedAt());

        assertEquals(1, order.getItems().size());
        assertEquals("P001", order.getItems().get(0).getProductCode());
    }

    @Test
    void shouldConvertOrderToResponseDTO() {

        Order order = new Order();

        order.setId("ORD-1");
        order.setUserEmail("test@test.com");
        order.setUserName("Patricio");
        order.setTotal(5000);
        order.setStatus(OrderStatus.PENDIENTE);

        OrderResponseDTO dto =
                OrderFactory.toResponse(order);

        assertNotNull(dto);

        assertEquals("ORD-1", dto.getId());
        assertEquals("test@test.com", dto.getUserEmail());
        assertEquals("Patricio", dto.getUserName());
        assertEquals(5000, dto.getTotal());
        assertEquals(OrderStatus.PENDIENTE, dto.getStatus());
    }
}