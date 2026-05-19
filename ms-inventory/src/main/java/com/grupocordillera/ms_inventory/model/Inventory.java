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

    private String code;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;

    private Map<String, Object> specs;
}