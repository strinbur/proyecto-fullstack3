package com.grupocordillera.ms_bff.inventory.service;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryResponseDTO> listar(String categoria);

    InventoryResponseDTO crear(InventoryCreateDTO dto);

    InventoryResponseDTO obtenerPorCodigo(String codigo);

    InventoryResponseDTO actualizar(String codigo, InventoryUpdateDTO dto);

    void eliminar(String codigo);
}