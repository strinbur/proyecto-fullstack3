package com.grupocordillera.ms_order.factory;

import com.grupocordillera.ms_order.dto.CartItemDTO;
import com.grupocordillera.ms_order.dto.CartResponseDTO;
import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.model.OrderItem;
import com.grupocordillera.ms_order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Fabrica de objetos para convertir datos de carrito a entidades de pedido y respuestas DTO.
 */
public class OrderFactory {

    /**
     * Crea una entidad {@link Order} a partir de los datos del usuario y el carrito.
     *
     * @param userEmail correo electrónico del usuario
     * @param userName  nombre del usuario
     * @param cart      datos del carrito de compras
     * @return entidad de orden inicializada
     */
    public static Order createOrder(String userEmail, String userName, CartResponseDTO cart) {
        Order order = new Order();
        
        order.setUserEmail(userEmail);
        order.setUserName(userName);
        order.setItems(mapItems(cart.getItems()));
        order.setTotal(cart.getTotal());
        order.setStatus(OrderStatus.PENDIENTE);
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }

    /**
     * Mapea los ítems del carrito a los ítems de la orden.
     *
     * @param items lista de ítems del carrito
     * @return lista de ítems de orden
     */
    private static List<OrderItem> mapItems(List<CartItemDTO> items) {
        return items.stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            
            orderItem.setProductCode(item.getProductCode());
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setCategory(item.getCategory());
            orderItem.setSubtotal(item.getSubtotal());
            
            return orderItem;
        }).toList();
    }

    /**
     * Convierte una entidad {@link Order} en su DTO de respuesta.
     *
     * @param order orden a convertir
     * @return DTO de respuesta de la orden
     */
    public static OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        
        dto.setId(order.getId());
        dto.setUserEmail(order.getUserEmail());
        dto.setUserName(order.getUserName());
        dto.setItems(order.getItems());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        return dto;
    }
} 