package com.grupocordillera.ms_inventory.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "productos")
public class Inventory {

    @Id
    private String id;

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