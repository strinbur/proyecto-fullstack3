package com.grupocordillera.ms_cart.dto;

import com.grupocordillera.ms_cart.model.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {

    private String userEmail;

    private List<CartItem> items;

    private double total;
}