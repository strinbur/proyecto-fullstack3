package com.grupocordillera.ms_bff.inventory.client;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-inventory", path = "/inventario")
public interface InventoryClient {

    @GetMapping
    List<InventoryResponseDTO> listar(
            @RequestParam(required = false) String categoria
    );

    @PostMapping
    InventoryResponseDTO crear(
            @RequestBody InventoryCreateDTO dto
    );

    @GetMapping("/codigo/{codigo}")
    InventoryResponseDTO obtenerPorCodigo(
            @PathVariable("codigo") String codigo
    );

    @PutMapping("/codigo/{codigo}")
    InventoryResponseDTO actualizar(
            @PathVariable("codigo") String codigo,
            @RequestBody InventoryUpdateDTO dto
    );

    @DeleteMapping("/codigo/{codigo}")
    void eliminar(
            @PathVariable("codigo") String codigo
    );
}