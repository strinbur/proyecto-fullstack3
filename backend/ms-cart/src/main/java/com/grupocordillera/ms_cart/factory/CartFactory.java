package com.grupocordillera.ms_cart.factory;

import com.grupocordillera.ms_cart.dto.CartResponseDTO;
import com.grupocordillera.ms_cart.model.Cart;

import java.util.ArrayList;

public class CartFactory {

    public static Cart createCart(String email) {

        Cart cart = new Cart();

        cart.setUserEmail(email);
        cart.setItems(new ArrayList<>());
        cart.setTotal(0);

        return cart;
    }

    public static CartResponseDTO toResponse(Cart cart) {

        CartResponseDTO dto =
                new CartResponseDTO();

        dto.setUserEmail(cart.getUserEmail());
        dto.setItems(cart.getItems());
        dto.setTotal(cart.getTotal());

        return dto;
    }
}