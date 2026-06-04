package com.grupocordillera.ms_inventory.service;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryResponseDTO> getAll();

    InventoryResponseDTO save(InventoryCreateDTO inventory);

    InventoryResponseDTO getByCode(String code);

    void deleteByCode(String code);

    InventoryResponseDTO update(
            String code,
            InventoryUpdateDTO inventory
    );

    List<InventoryResponseDTO> getByCategory(String category);
}