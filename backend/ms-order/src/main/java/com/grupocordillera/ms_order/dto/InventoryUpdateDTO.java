package com.grupocordillera.ms_order.dto;

import lombok.Data;

/**
 * DTO utilizado para actualizar información de inventario.
 */
@Data
public class InventoryUpdateDTO {

    /**
     * Nombre del producto.
     */
    private String name;

    /**
     * Marca del producto.
     */
    private String brand;

    /**
     * Precio del producto.
     */
    private double price;

    /**
     * Cantidad disponible del producto.
     */
    private int quantity;

    /**
     * Categoría del producto.
     */
    private String category;
} 