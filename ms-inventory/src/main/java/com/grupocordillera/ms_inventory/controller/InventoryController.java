package com.grupocordillera.ms_inventory.controller;

import com.grupocordillera.ms_inventory.model.Inventory;
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
    public ResponseEntity<List<Inventory>> listar(
            @RequestParam(required = false) String categoria) {

        if (categoria != null && !categoria.trim().isEmpty()) {
            return ResponseEntity.ok(service.obtenerPorCategoria(categoria));
        }

        return ResponseEntity.ok(service.obtenerTodos());
    }


    @PostMapping
    public ResponseEntity<Inventory> crear(
            @Valid @RequestBody Inventory inventario) {

        Inventory nuevo = service.guardar(inventario);
        return ResponseEntity.status(201).body(nuevo);
    }


    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Inventory> obtenerPorCodigo(
            @PathVariable String codigo) {

        return ResponseEntity.ok(service.obtenerPorCodigo(codigo));
    }


    @DeleteMapping("/codigo/{codigo}")
    public ResponseEntity<Void> eliminarPorCodigo(
            @PathVariable String codigo) {

        service.eliminarPorCodigo(codigo);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/codigo/{codigo}")
    public ResponseEntity<Inventory> actualizar(
            @PathVariable String codigo,
            @Valid @RequestBody Inventory inventario) {

        return ResponseEntity.ok(service.actualizar(codigo, inventario));
    }
}