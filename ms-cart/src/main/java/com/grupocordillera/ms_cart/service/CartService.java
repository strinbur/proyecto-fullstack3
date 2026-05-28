package com.grupocordillera.ms_cart.service;

import com.grupocordillera.ms_cart.dto.AddProductDTO;
import com.grupocordillera.ms_cart.dto.CartResponseDTO;
import com.grupocordillera.ms_cart.dto.UpdateQuantityDTO;

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