package com.grupocordillera.ms_inventory.controller;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.service.InventoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> listar(
            @RequestParam(required = false) String categoria
    ) {

        if (categoria != null && !categoria.trim().isEmpty()) {
            return ResponseEntity.ok(
                    service.obtenerPorCategoria(categoria)
            );
        }

        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> crear(
            @Valid @RequestBody InventoryCreateDTO inventario
    ) {

        InventoryResponseDTO nuevo =
                service.guardar(inventario);

        return ResponseEntity
                .status(201)
                .body(nuevo);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<InventoryResponseDTO> obtenerPorCodigo(
            @PathVariable String codigo
    ) {

        return ResponseEntity.ok(
                service.obtenerPorCodigo(codigo)
        );
    }

    @DeleteMapping("/codigo/{codigo}")
    public ResponseEntity<Void> eliminarPorCodigo(
            @PathVariable String codigo
    ) {

        service.eliminarPorCodigo(codigo);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/codigo/{codigo}")
    public ResponseEntity<InventoryResponseDTO> actualizar(
            @PathVariable String codigo,
            @Valid @RequestBody InventoryUpdateDTO inventario
    ) {

        return ResponseEntity.ok(
                service.actualizar(codigo, inventario)
        );
    }
}