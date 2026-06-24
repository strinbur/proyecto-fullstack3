package com.grupocordillera.ms_order.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {

    private String userEmail;
    private List<CartItemDTO> items;
    private double total;
}