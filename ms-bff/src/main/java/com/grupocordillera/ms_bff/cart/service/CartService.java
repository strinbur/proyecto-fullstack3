package com.grupocordillera.ms_bff.cart.service;

import com.grupocordillera.ms_bff.cart.dto.*;

public interface CartService {

    CartResponseDTO getCart();

    CartResponseDTO addProduct(AddProductDTO dto);

    CartResponseDTO updateQuantity(
            String code,
            UpdateQuantityDTO dto
    );

    CartResponseDTO removeProduct(String code);

    void clearCart();
}