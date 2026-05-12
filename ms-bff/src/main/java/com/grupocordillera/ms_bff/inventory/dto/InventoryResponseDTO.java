package com.grupocordillera.ms_bff.inventory.dto;

import lombok.Data;

import java.util.Map;

@Data
public class InventoryResponseDTO {

    private String id;
    private String codigo;
    private String nombre;
    private String marca;
    private double precio;
    private int cantidad;
    private String categoria;

    private Map<String, Object> atributos;
}