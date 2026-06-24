package com.grupocordillera.ms_bff.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class InventoryUpdateDTO {

    /** Nombre del producto actualizado. */
    @NotBlank(message = "Error, falta nombre del producto")
    private String name;

    /** Marca del producto actualizado. */
    @NotBlank(message = "Error, falta marca del producto")
    private String brand;

    /** Precio actualizado (>=0). */
    @PositiveOrZero(message = "Error, precio no valido")
    private double price;

    /** Cantidad actualizada en stock. */
    @Min(value = 0, message = "Error, cantidad ingresada no valida")
    private int quantity;

    /** Categoría actualizada. */
    @NotBlank(message = "Error, falta categoria del producto")
    private String category;

    /** Especificaciones adicionales actualizables. */
    private Map<String, Object> specs;
}