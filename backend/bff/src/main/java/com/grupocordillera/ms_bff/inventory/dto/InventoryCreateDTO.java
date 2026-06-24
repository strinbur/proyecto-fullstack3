package com.grupocordillera.ms_bff.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class InventoryCreateDTO {

    /** Código único del producto. */
    @NotBlank(message = "Error, falta codigo del producto")
    private String code;

    /** Nombre del producto. */
    @NotBlank(message = "Error, falta nombre del producto")
    private String name;

    /** Marca del producto. */
    @NotBlank(message = "Error, falta marca del producto")
    private String brand;

    /** Precio unitario (>= 0). */
    @PositiveOrZero(message = "Error, precio no valido")
    private double price;

    /** Cantidad inicial en stock. */
    @Min(value = 0, message = "Error, cantidad ingresada no valida")
    private int quantity;

    /** Categoría del producto. */
    @NotBlank(message = "Error, falta categoria del producto")
    private String category;

    /** Especificaciones adicionales (mapa libre). */
    private Map<String, Object> specs;
}