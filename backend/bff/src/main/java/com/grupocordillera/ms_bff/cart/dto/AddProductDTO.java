package com.grupocordillera.ms_bff.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
/**
 * DTO utilizado por el BFF para solicitar la adición de un producto al
 * carrito. Contiene el código del producto y la cantidad solicitada.
 */
public class AddProductDTO {

    /** Código único del producto. */
    @NotBlank(message = "Codigo obligatorio")
    private String productCode;

    /** Cantidad a agregar (mínimo 1). */
    @Min(value = 1, message = "Cantidad invalida")
    private int quantity;
}