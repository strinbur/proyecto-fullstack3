package com.grupocordillera.ms_bff.order.dto;

import lombok.Data;

@Data
/**
 * Representa un ítem individual dentro de una orden. Usado por el BFF
 * para transportar información resumida hacia el frontend.
 */
public class OrderItemDTO {

    /** Código único del producto. */
    private String productCode;

    /** Nombre del producto en la orden. */
    private String name;

    /** Precio unitario al momento de la orden. */
    private double price;

    /** Cantidad ordenada. */
    private int quantity;

    /** Categoría del producto. */
    private String category;

    /** Subtotal calculado (price * quantity). */
    private double subtotal;
}