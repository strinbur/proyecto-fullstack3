package com.grupocordillera.ms_inventory.service;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryResponseDTO> obtenerTodos();

    InventoryResponseDTO guardar(InventoryCreateDTO inventario);

    InventoryResponseDTO obtenerPorCodigo(String codigo);

    void eliminarPorCodigo(String codigo);

    InventoryResponseDTO actualizar(
            String codigo,
            InventoryUpdateDTO inventario
    );

    List<InventoryResponseDTO> obtenerPorCategoria(String categoria);
}