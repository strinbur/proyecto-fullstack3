package com.grupocordillera.ms_bff.inventory.controller;

import com.grupocordillera.ms_bff.inventory.dto.InventoryDTO;
import com.grupocordillera.ms_bff.inventory.service.InventoryService;

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

    @GetMapping
    public List<InventoryDTO> listar(
            @RequestParam(required = false) String categoria
    ) {

        log.info("📦 BFF LISTAR INVENTARIO");

        return service.listar(categoria);
    }

    @PostMapping
    public InventoryDTO crear(
            @RequestBody InventoryDTO dto
    ) {

        log.info("🟢 BFF CREAR PRODUCTO");

        return service.crear(dto);
    }

    @GetMapping("/codigo/{codigo}")
    public InventoryDTO obtenerPorCodigo(
            @PathVariable String codigo
    ) {

        log.info("🔍 BFF PRODUCTO CODIGO: {}", codigo);

        return service.obtenerPorCodigo(codigo);
    }

    @PutMapping("/codigo/{codigo}")
    public InventoryDTO actualizar(
            @PathVariable String codigo,
            @RequestBody InventoryDTO dto
    ) {

        log.info("✏️ BFF UPDATE PRODUCTO: {}", codigo);

        return service.actualizar(codigo, dto);
    }

    @DeleteMapping("/codigo/{codigo}")
    public void eliminar(
            @PathVariable String codigo
    ) {

        log.info("🗑️ BFF DELETE PRODUCTO: {}", codigo);

        service.eliminar(codigo);
    }
}