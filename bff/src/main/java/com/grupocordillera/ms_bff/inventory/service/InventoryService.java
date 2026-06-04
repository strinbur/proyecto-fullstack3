package com.grupocordillera.ms_bff.inventory.service;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryResponseDTO> getAll(String category);

    InventoryResponseDTO save(InventoryCreateDTO dto);

    InventoryResponseDTO getByCode(String code);

    InventoryResponseDTO update(String code, InventoryUpdateDTO dto);

    void deleteByCode(String code);
}