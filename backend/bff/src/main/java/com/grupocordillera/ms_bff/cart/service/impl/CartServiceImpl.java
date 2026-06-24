package com.grupocordillera.ms_bff.cart.service.impl;

import com.grupocordillera.ms_bff.cart.client.CartClient;
import com.grupocordillera.ms_bff.cart.dto.*;
import com.grupocordillera.ms_bff.cart.service.CartService;

import org.springframework.stereotype.Service;

@Service
/**
 * Implementación del `CartService` en el BFF que delega llamadas al
 * microservicio de carrito (`ms-cart`) mediante un cliente Feign.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartClient client;

    public CartServiceImpl(CartClient client) {
        this.client = client;
    }

    @Override
    public CartResponseDTO getCart() {
        return client.getCart();
    }

    @Override
    public CartResponseDTO addProduct(AddProductDTO dto) {
        return client.addProduct(dto);
    }

    @Override
    public CartResponseDTO updateQuantity(
            String code,
            UpdateQuantityDTO dto
    ) {
        return client.updateQuantity(code, dto);
    }

    @Override
    public CartResponseDTO removeProduct(String code) {
        return client.removeProduct(code);
    }

    @Override
    public void clearCart() {
        client.clearCart();
    }
}