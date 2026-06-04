package com.grupocordillera.ms_order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrderDTO {

    @NotBlank(message = "Nombre obligatorio")
    private String customerName;
}