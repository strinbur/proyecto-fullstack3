package com.grupocordillera.ms_order.dto;

import lombok.Data;

/**
 * DTO que representa un artículo dentro del carrito de compras.
 */
@Data
public class CartItemDTO {

    /**
     * Código único del producto.
     */
    private String productCode;

    /**
     * Nombre del producto.
     */
    private String name;

    /**
     * Precio unitario del producto.
     */
    private double price;

    /**
     * Cantidad pedida del producto.
     */
    private int quantity;

    /**
     * Categoría del producto.
     */
    private String category;

    /**
     * Subtotal para el artículo (precio * cantidad).
     */
    private double subtotal;
} 