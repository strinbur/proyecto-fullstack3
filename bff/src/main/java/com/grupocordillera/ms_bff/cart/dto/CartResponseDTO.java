package com.grupocordillera.ms_bff.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {

    private String userEmail;
    private List<CartItemDTO> items;
    private double total;
}