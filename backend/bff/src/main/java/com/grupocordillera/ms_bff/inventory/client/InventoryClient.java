package com.grupocordillera.ms_bff.inventory.client;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cliente Feign para proxear llamadas al microservicio `ms-inventory`.
 */
@FeignClient(name = "ms-inventory", path = "/inventory")
public interface InventoryClient {

    @GetMapping
    List<InventoryResponseDTO> getAll(
            @RequestParam(required = false) String category
    );

    @PostMapping
    InventoryResponseDTO save(
            @RequestBody InventoryCreateDTO dto
    );

    @GetMapping("/code/{code}")
    InventoryResponseDTO getByCode(
            @PathVariable("code") String code
    );

    @PutMapping("/code/{code}")
    InventoryResponseDTO update(
            @PathVariable("code") String code,
            @RequestBody InventoryUpdateDTO dto
    );

    @DeleteMapping("/code/{code}")
    void deleteByCode(
            @PathVariable("code") String code
    );
}