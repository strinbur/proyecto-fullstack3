/**
 * DTO para solicitar la adición de un producto al carrito.
 */
package com.grupocordillera.ms_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddProductDTO {

    /**
     * Código único del producto a agregar.
     */
    @NotBlank(message = "Codigo obligatorio")
    private String productCode;

    /**
     * Cantidad del producto que se desea agregar.
     */
    @Min(value = 1, message = "Cantidad invalida")
    private int quantity;
}