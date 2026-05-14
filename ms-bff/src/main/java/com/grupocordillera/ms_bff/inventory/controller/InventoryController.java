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

    // LISTAR
    @GetMapping
    public List<InventoryResponseDTO> listar(
            @RequestParam(required = false) String categoria
    ) {
        log.info("BFF LISTAR INVENTORY - categoria: {}", categoria);

        return service.listar(categoria);
    }

    // CREAR
    @PostMapping
    public InventoryResponseDTO crear(
            @Valid @RequestBody InventoryCreateDTO dto
    ) {
        log.info("BFF CREAR PRODUCTO - codigo: {}", dto.getCodigo());

        return service.crear(dto);
    }

    // GET POR CODIGO
    @GetMapping("/codigo/{codigo}")
    public InventoryResponseDTO obtenerPorCodigo(
            @PathVariable String codigo
    ) {
        log.info("BFF GET PRODUCTO CODIGO: {}", codigo);

        return service.obtenerPorCodigo(codigo);
    }

    // UPDATE
    @PutMapping("/codigo/{codigo}")
    public InventoryResponseDTO actualizar(
            @PathVariable String codigo,
            @Valid @RequestBody InventoryUpdateDTO dto
    ) {
        log.info("BFF UPDATE PRODUCTO CODIGO: {}", codigo);

        return service.actualizar(codigo, dto);
    }

    // DELETE
    @DeleteMapping("/codigo/{codigo}")
    public void eliminar(
            @PathVariable String codigo
    ) {
        log.info("BFF DELETE PRODUCTO CODIGO: {}", codigo);

        service.eliminar(codigo);
    }
}