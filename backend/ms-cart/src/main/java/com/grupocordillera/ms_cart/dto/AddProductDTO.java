package com.grupocordillera.ms_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddProductDTO {

    @NotBlank(message = "Codigo obligatorio")
    private String productCode;

    @Min(value = 1, message = "Cantidad invalida")
    private int quantity;
}