package com.grupocordillera.ms_bff.cart.dto;

import lombok.Data;

import java.util.List;

@Data
/**
 * DTO devuelto por los endpoints del carrito con el estado completo del mismo.
 */
public class CartResponseDTO {

    /** Email del usuario propietario del carrito. */
    private String userEmail;

    /** Lista de ítems contenidos en el carrito. */
    private List<CartItemDTO> items;

    /** Total calculado del carrito. */
    private double total;
}