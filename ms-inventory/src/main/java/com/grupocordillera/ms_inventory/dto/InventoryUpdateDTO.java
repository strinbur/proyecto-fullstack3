package com.grupocordillera.ms_inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class InventoryUpdateDTO {

    @NotBlank(message = "Error, falta nombre del producto")
    private String name;

    @NotBlank(message = "Error, falta marca del producto")
    private String brand;

    @PositiveOrZero(message = "Error, precio no valido")
    private double price;

    @Min(value = 0, message = "Error, cantidad ingresada no valida")
    private int quantity;

    @NotBlank(message = "Error, falta categoria del producto")
    private String category;

    private Map<String, Object> specs;
}