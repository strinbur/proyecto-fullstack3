package com.grupocordillera.ms_order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de orden que representa un pedido almacenado en MongoDB.
 */
@Data
@Document(collection = "orders")
public class Order {

    /**
     * Identificador único de la orden.
     */
    @Id
    private String id;

    /**
     * Correo electrónico del usuario que generó la orden.
     */
    private String userEmail;

    /**
     * Nombre del usuario que generó la orden.
     */
    private String userName;

    /**
     * Detalle de los ítems incluidos en la orden.
     */
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Total calculado de la orden.
     */
    private double total;

    /**
     * Estado actual de la orden.
     */
    private OrderStatus status;

    /**
     * Fecha y hora de creación de la orden.
     */
    private LocalDateTime createdAt;
}