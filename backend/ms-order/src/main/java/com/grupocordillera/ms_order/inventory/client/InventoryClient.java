package com.grupocordillera.ms_order.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.grupocordillera.ms_order.dto.InventoryResponseDTO;
import com.grupocordillera.ms_order.dto.InventoryUpdateDTO;

/**
 * Cliente Feign para comunicarse con el servicio de inventario.
 */
@FeignClient(name = "ms-inventory", url = "${inventory.url}", path = "/inventory")
public interface InventoryClient {

    /**
     * Obtiene un producto del inventario por su código.
     *
     * @param code código del producto
     * @return detalles del producto en el inventario
     */
    @GetMapping("/code/{code}")
    InventoryResponseDTO getByCode(@PathVariable("code") String code);

    /**
     * Actualiza los datos de inventario de un producto por su código.
     *
     * @param code código del producto
     * @param dto  datos de actualización del inventario
     * @return producto actualizado
     */
    @PutMapping("/code/{code}")
    InventoryResponseDTO update(@PathVariable("code") String code, @RequestBody InventoryUpdateDTO dto);
}