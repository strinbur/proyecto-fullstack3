package com.grupocordillera.ms_bff.order.dto;

import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

@Data
/**
 * DTO que representa una orden completa que se devuelve al frontend.
 */
public class OrderResponseDTO {

    /** Identificador de la orden. */
    private String id;

    /** Email del usuario que realizó la orden. */
    private String userEmail;

    /** Nombre del usuario que realizó la orden. */
    private String userName;

    /** Lista de ítems incluidos en la orden. */
    private List<OrderItemDTO> items;

    /** Total de la orden. */
    private double total;

    /** Estado de la orden (ej. PENDING, COMPLETED). */
    private String status;

    /** Fecha y hora de creación de la orden. */
    private LocalDateTime createdAt;
}