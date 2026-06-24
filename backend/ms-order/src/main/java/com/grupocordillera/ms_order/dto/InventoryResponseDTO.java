package com.grupocordillera.ms_order.dto;

import lombok.Data;

/**
 * DTO para la respuesta del inventario con la información del producto.
 */
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
     * Precio actual del producto.
     */
    private double price;

    /**
     * Cantidad disponible en inventario.
     */
    private int quantity;

    /**
     * Categoría del producto.
     */
    private String category;

    /**
     * Marca del producto.
     */
    private String brand;
} 