package com.grupocordillera.ms_inventory.controller;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.service.InventoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Trae los productos
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll(
            @RequestParam(required = false) String category
    ) {

        if (category != null && !category.trim().isEmpty()) {

            return ResponseEntity.ok(
                    service.getByCategory(category)
            );
        }

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // Solo ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> create(
            @Valid @RequestBody InventoryCreateDTO inventory
    ) {

        InventoryResponseDTO newInventory =
                service.save(inventory);

        return ResponseEntity
                .status(201)
                .body(newInventory);
    }

    // Trae un producto por su codigo
    @GetMapping("/code/{code}")
    public ResponseEntity<InventoryResponseDTO> getByCode(
            @PathVariable String code
    ) {

        return ResponseEntity.ok(
                service.getByCode(code)
        );
    }

    // Solo ADMIN puede eliminar un producto por su codigo
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/code/{code}")
    public ResponseEntity<Void> deleteByCode(
            @PathVariable String code
    ) {

        service.deleteByCode(code);

        return ResponseEntity.noContent().build();
    }

    // Solo ADMIN puede actualizar un producto por su codigo
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/code/{code}")
    public ResponseEntity<InventoryResponseDTO> update(
            @PathVariable String code,
            @Valid @RequestBody InventoryUpdateDTO inventory
    ) {

        return ResponseEntity.ok(
                service.update(code, inventory)
        );
    }
}