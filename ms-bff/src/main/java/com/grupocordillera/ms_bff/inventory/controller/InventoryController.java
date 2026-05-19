package com.grupocordillera.ms_bff.inventory.controller;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_bff.inventory.service.InventoryService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/inventory")
@CrossOrigin
public class InventoryController {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Get all
    @GetMapping
    public List<InventoryResponseDTO> getAll(
            @RequestParam(required = false) String category
    ) {

        log.info("BFF GET ALL INVENTORY - category: {}", category);

        return service.getAll(category);
    }

    // Save
    @PostMapping
    public InventoryResponseDTO save(
            @Valid @RequestBody InventoryCreateDTO dto
    ) {

        log.info("BFF SAVE PRODUCT - code: {}", dto.getCode());

        return service.save(dto);
    }

    // Get by code
    @GetMapping("/code/{code}")
    public InventoryResponseDTO getByCode(
            @PathVariable String code
    ) {

        log.info("BFF GET PRODUCT BY CODE: {}", code);

        return service.getByCode(code);
    }

    // Update necesita el codigo para actualizar el producto
    @PutMapping("/code/{code}")
    public InventoryResponseDTO update(
            @PathVariable String code,
            @Valid @RequestBody InventoryUpdateDTO dto
    ) {

        log.info("BFF UPDATE PRODUCT BY CODE: {}", code);

        return service.update(code, dto);
    }

    // Delete necesita el codigo para eliminar el producto
    @DeleteMapping("/code/{code}")
    public void deleteByCode(
            @PathVariable String code
    ) {

        log.info("BFF DELETE PRODUCT BY CODE: {}", code);

        service.deleteByCode(code);
    }
}