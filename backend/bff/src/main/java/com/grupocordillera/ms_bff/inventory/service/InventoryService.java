package com.grupocordillera.ms_bff.inventory.service;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;

import java.util.List;

/**
 * Servicio del BFF para operaciones de inventario. Implementaciones suelen
 * delegar en un cliente Feign hacia `ms-inventory`.
 */
public interface InventoryService {

    /** Recupera productos, opcionalmente filtrados por categoría. */
    List<InventoryResponseDTO> getAll(String category);

    /** Crea un nuevo producto en inventario. */
    InventoryResponseDTO save(InventoryCreateDTO dto);

    /** Recupera un producto por su código. */
    InventoryResponseDTO getByCode(String code);

    /** Actualiza un producto identificado por código. */
    InventoryResponseDTO update(String code, InventoryUpdateDTO dto);

    /** Elimina un producto por su código. */
    void deleteByCode(String code);
}