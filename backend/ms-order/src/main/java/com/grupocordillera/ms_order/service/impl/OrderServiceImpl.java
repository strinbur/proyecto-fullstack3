package com.grupocordillera.ms_order.service.impl;

import com.grupocordillera.ms_order.cart.client.CartClient;
import com.grupocordillera.ms_order.dto.CartResponseDTO;
import com.grupocordillera.ms_order.dto.InventoryResponseDTO;
import com.grupocordillera.ms_order.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.exception.OrderException;
import com.grupocordillera.ms_order.factory.OrderFactory;
import com.grupocordillera.ms_order.inventory.client.InventoryClient;
import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.model.OrderStatus;
import com.grupocordillera.ms_order.repository.OrderRepository;
import com.grupocordillera.ms_order.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final InventoryClient inventoryClient;

    public OrderServiceImpl(OrderRepository orderRepository, CartClient cartClient, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.cartClient = cartClient;
        this.inventoryClient = inventoryClient;
    }

    /**
     * Obtiene los datos del usuario autenticado desde el contexto de seguridad.
     *
     * @return mapa con información del usuario autenticado
     */
    private Map<String, String> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new OrderException("Usuario no autenticado");
        }
        return (Map<String, String>) auth.getPrincipal();
    }

    /**
     * Crea una nueva orden a partir del carrito del usuario autenticado.
     *
     * @return orden creada
     */
    @Override
    public OrderResponseDTO createOrder() {
        Map<String, String> user = getAuthenticatedUser();
        String userEmail = user.get("email");
        String userName  = user.get("userName");

        CartResponseDTO cart = cartClient.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new OrderException("Carrito vacío");
        }

        for (var item : cart.getItems()) {
            InventoryResponseDTO product = inventoryClient.getByCode(item.getProductCode());

            if (product.getQuantity() < item.getQuantity()) {
                throw new OrderException("Stock insuficiente: " + item.getProductCode());
            }

            InventoryUpdateDTO update = new InventoryUpdateDTO();
            update.setName(product.getName());
            update.setPrice(product.getPrice());
            update.setQuantity(product.getQuantity() - item.getQuantity());
            update.setCategory(product.getCategory());
            update.setBrand(product.getBrand());

            inventoryClient.update(item.getProductCode(), update);
        }

        Order order = OrderFactory.createOrder(userEmail, userName, cart);
        Order saved = orderRepository.save(order);

        return OrderFactory.toResponse(saved);
    }

    /**
     * Recupera las órdenes asociadas a un correo de usuario.
     *
     * @param userEmail correo electrónico del usuario
     * @return lista de órdenes del usuario
     */
    @Override
    public List<OrderResponseDTO> getOrdersByUser(String userEmail) {
        return orderRepository.findByUserEmail(userEmail).stream()
                .map(OrderFactory::toResponse)
                .toList();
    }

    /**
     * Recupera todas las órdenes almacenadas.
     *
     * @return lista de todas las órdenes
     */
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderFactory::toResponse)
                .toList();
    }

    /**
     * Recupera una orden por su identificador.
     *
     * @param id identificador de la orden
     * @return orden encontrada
     */
    @Override
    public OrderResponseDTO getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada"));

        return OrderFactory.toResponse(order);
    }

    /**
     * Recupera las órdenes que coinciden con un estado específico.
     *
     * @param status estado de la orden
     * @return lista de órdenes en ese estado
     */
    @Override
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrderException("Estado inválido: " + status);
        }

        return orderRepository.findByStatus(orderStatus).stream()
                .map(OrderFactory::toResponse)
                .toList();
    }

    /**
     * Actualiza el estado de una orden y, si se cancela una orden pendiente,
     * repone el inventario correspondiente.
     *
     * @param id     identificador de la orden
     * @param status nuevo estado de la orden
     * @return orden actualizada
     */
    @Override
    public OrderResponseDTO updateOrderStatus(String id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada"));

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrderException("Estado inválido: " + status);
        }

        if (order.getStatus() == OrderStatus.CANCELADO) {
            throw new OrderException("No se puede modificar una orden cancelada");
        }

        if (order.getStatus() == OrderStatus.COMPLETADO && newStatus == OrderStatus.CANCELADO) {
            throw new OrderException("No se puede cancelar una orden completada");
        }

        if (order.getStatus() == newStatus) {
            throw new OrderException("La orden ya se encuentra en estado " + newStatus);
        }

        if (newStatus == OrderStatus.CANCELADO && order.getStatus() == OrderStatus.PENDIENTE) {
            for (var item : order.getItems()) {
                InventoryResponseDTO product = inventoryClient.getByCode(item.getProductCode());

                InventoryUpdateDTO update = new InventoryUpdateDTO();
                update.setName(product.getName());
                update.setBrand(product.getBrand());
                update.setPrice(product.getPrice());
                update.setCategory(product.getCategory());
                update.setQuantity(product.getQuantity() + item.getQuantity());

                inventoryClient.update(item.getProductCode(), update);
            }
        }

        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        return OrderFactory.toResponse(saved);
    }
}