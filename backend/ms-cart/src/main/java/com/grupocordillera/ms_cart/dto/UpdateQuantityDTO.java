package com.grupocordillera.ms_cart.dto;

/**
 * DTO para solicitar la actualización de la cantidad de un producto en el carrito.
 */
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateQuantityDTO {

    /**
     * Nueva cantidad del producto en el carrito.
     */
    @Min(value = 1, message = "Cantidad invalida")
    private int quantity;
}