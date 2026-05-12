package com.grupocordillera.ms_bff.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class InventoryCreateDTO {

    @NotBlank(message = "Error, falta codigo del producto")
    private String codigo;

    @NotBlank(message = "Error, falta nombre del producto")
    private String nombre;

    @NotBlank(message = "Error, falta marca del producto")
    private String marca;

    @PositiveOrZero(message = "Error, precio no valido")
    private double precio;

    @Min(value = 0, message = "Error, cantidad ingresada no valida")
    private int cantidad;

    @NotBlank(message = "Error, falta categoria del producto")
    private String categoria;

    private Map<String, Object> atributos;
}