/**
 * DTO de respuesta que representa el estado actual del carrito.
 */
package com.grupocordillera.ms_cart.dto;

import com.grupocordillera.ms_cart.model.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {

    /**
     * Correo electrónico del usuario propietario del carrito.
     */
    private String userEmail;

    /**
     * Lista de ítems presentes en el carrito.
     */
    private List<CartItem> items;

    /**
     * Total del carrito calculado sobre los ítems.
     */
    private double total;
}