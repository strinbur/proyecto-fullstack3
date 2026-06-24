/**
 * Modelo que representa un ítem dentro de un carrito de compras.
 */
package com.grupocordillera.ms_cart.model;

import lombok.Data;

@Data
public class CartItem {

    /**
     * Código único del producto en el carrito.
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
     * Cantidad de unidades del producto en el carrito.
     */
    private int quantity;

    /**
     * Categoría del producto.
     */
    private String category;

    /**
     * Subtotal del ítem (precio * cantidad).
     */
    private double subtotal;
}