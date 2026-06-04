package com.grupocordillera.ms_bff.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateQuantityDTO {

    @Min(value = 1, message = "Cantidad invalida")
    private int quantity;
}