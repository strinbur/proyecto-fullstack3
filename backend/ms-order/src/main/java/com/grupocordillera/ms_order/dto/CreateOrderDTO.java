package com.grupocordillera.ms_order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para los datos necesarios al crear una orden.
 */
@Data
public class CreateOrderDTO {

    @NotBlank(message = "Nombre obligatorio")
    private String customerName;
} 