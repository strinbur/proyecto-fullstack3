package com.grupocordillera.ms_bff.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
/**
 * DTO para actualizar la cantidad de un producto en el carrito.
 */
public class UpdateQuantityDTO {

    /** Nueva cantidad deseada (mínimo 1). */
    @Min(value = 1, message = "Cantidad invalida")
    private int quantity;
}