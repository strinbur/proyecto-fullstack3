package com.grupocordillera.ms_bff.inventory.dto;

import lombok.Data;

import java.util.Map;

@Data
public class InventoryResponseDTO {
    /** Identificador interno (DB). */
    private String id;

    /** Código único del producto. */
    private String code;

    /** Nombre del producto. */
    private String name;

    /** Marca del producto. */
    private String brand;

    /** Precio unitario. */
    private double price;

    /** Cantidad disponible en stock. */
    private int quantity;

    /** Categoría del producto. */
    private String category;

    /** Especificaciones adicionales. */
    private Map<String, Object> specs;
}