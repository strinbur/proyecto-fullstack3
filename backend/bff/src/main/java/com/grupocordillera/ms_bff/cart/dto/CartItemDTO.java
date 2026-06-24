package com.grupocordillera.ms_bff.cart.dto;

import lombok.Data;

@Data
/**
 * Representa un ítem dentro del carrito mostrado al frontend.
 */
public class CartItemDTO {

    /** Código del producto. */
    private String productCode;

    /** Nombre del producto. */
    private String name;

    /** Precio unitario. */
    private double price;

    /** Cantidad en el carrito. */
    private int quantity;

    /** Categoría del producto. */
    private String category;

    /** Subtotal calculado (price * quantity). */
    private double subtotal;
}