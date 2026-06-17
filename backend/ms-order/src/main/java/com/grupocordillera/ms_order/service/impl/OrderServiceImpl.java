package com.grupocordillera.ms_order.service.impl;

import com.grupocordillera.ms_order.cart.client.CartClient;
import com.grupocordillera.ms_order.cart.client.CartResponseDTO;
import com.grupocordillera.ms_order.dto.OrderResponseDTO;
import com.grupocordillera.ms_order.common.exception.OrderException;
import com.grupocordillera.ms_order.factory.OrderFactory;
import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.repository.OrderRepository;
import com.grupocordillera.ms_order.service.OrderService;
import org.springframework.stereotype.Service;
import com.grupocordillera.ms_order.inventory.client.InventoryClient;
import com.grupocordillera.ms_order.inventory.client.InventoryResponseDTO;
import com.grupocordillera.ms_order.inventory.client.InventoryUpdateDTO;

import java.util.List;

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

    @Override
    public OrderResponseDTO createOrder(String userEmail, String userName) {
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

    @Override
    public List<OrderResponseDTO> getOrdersByUser(String userEmail) {
        return orderRepository.findByUserEmail(userEmail).stream()
                .map(OrderFactory::toResponse)
                .toList();
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderFactory::toResponse)
                .toList();
    }

    @Override
    public OrderResponseDTO getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada"));

        return OrderFactory.toResponse(order);
    }
}