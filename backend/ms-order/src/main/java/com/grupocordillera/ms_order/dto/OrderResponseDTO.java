package com.grupocordillera.ms_order.dto;

import com.grupocordillera.ms_order.model.OrderItem;
import com.grupocordillera.ms_order.model.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta de una orden con sus detalles y estado.
 */
@Data
public class OrderResponseDTO {

    /**
     * Identificador único de la orden.
     */
    private String id;

    /**
     * Correo electrónico del usuario que realizó la orden.
     */
    private String userEmail;

    /**
     * Nombre del usuario que realizó la orden.
     */
    private String userName;

    /**
     * Lista de ítems incluidos en la orden.
     */
    private List<OrderItem> items;

    /**
     * Total de la orden.
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