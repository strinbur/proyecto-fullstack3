package com.grupocordillera.ms_order.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO de respuesta del carrito que contiene los ítems y el total.
 */
@Data
public class CartResponseDTO {

    /**
     * Correo electrónico del usuario propietario del carrito.
     */
    private String userEmail;

    /**
     * Lista de artículos en el carrito.
     */
    private List<CartItemDTO> items;

    /**
     * Total del carrito.
     */
    private double total;
} 