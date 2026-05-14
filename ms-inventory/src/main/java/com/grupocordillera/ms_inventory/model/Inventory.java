package com.grupocordillera.ms_inventory.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "products")
public class Inventory {

    @Id
    private String id;

    private String codigo;
    private String nombre;
    private String marca;
    private double precio;
    private int cantidad;
    private String categoria;

    private Map<String, Object> atributos;
}