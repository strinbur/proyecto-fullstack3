package com.grupocordillera.ms_cart.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.grupocordillera.ms_cart.dto.InventoryResponseDTO;

/**
 * Cliente Feign para comunicarse con el servicio de inventario.
 */
@FeignClient(
        name = "ms-inventory",
        url = "${inventory.url}",
        path = "/inventory"
)
public interface InventoryClient {

    /**
     * Obtiene la información de inventario de un producto por su código.
     *
     * @param code código del producto
     * @return DTO con los datos de inventario del producto
     */
    @GetMapping("/code/{code}")
    InventoryResponseDTO getByCode(
            @PathVariable("code") String code
    );
}