package com.grupocordillera.ms_bff.inventory.client;

import com.grupocordillera.ms_bff.inventory.dto.InventoryDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-inventory", url = "http://localhost:8082")
public interface InventoryClient {

    @GetMapping("/inventario")
    List<InventoryDTO> listar(
            @RequestParam(required = false) String categoria
    );

    @PostMapping("/inventario")
    InventoryDTO crear(
            @RequestBody InventoryDTO dto
    );

    @GetMapping("/inventario/codigo/{codigo}")
    InventoryDTO obtenerPorCodigo(
            @PathVariable("codigo") String codigo
    );

    @PutMapping("/inventario/codigo/{codigo}")
    InventoryDTO actualizar(
            @PathVariable("codigo") String codigo,
            @RequestBody InventoryDTO dto
    );

    @DeleteMapping("/inventario/codigo/{codigo}")
    Void eliminar(
            @PathVariable("codigo") String codigo
    );
}