/**
 * DTO que representa la información de inventario de un producto.
 */
package com.grupocordillera.ms_cart.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {

    /**
     * Código único del producto.
     */
    private String code;

    /**
     * Nombre del producto.
     */
    private String name;

    /**
     * Precio unitario del producto.
     */
    private double price;

    /**
     * Cantidad disponible en inventario.
     */
    private int quantity;

    /**
     * Categoría a la que pertenece el producto.
     */
    private String category;
}