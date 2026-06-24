package com.grupocordillera.ms_cart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Documento de carrito persistido en MongoDB.
 */
@Data
@Document(collection = "carts")
public class Cart {

    /**
     * Identificador único del carrito.
     */
    @Id
    private String id;

    /**
     * Correo electrónico del propietario del carrito.
     */
    private String userEmail;

    /**
     * Lista de productos agregados al carrito.
     */
    private List<CartItem> items = new ArrayList<>();

    /**
     * Total calculado del carrito.
     */
    private double total;
}