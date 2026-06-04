package com.grupocordillera.ms_bff.inventory.service.impl;

import com.grupocordillera.ms_bff.inventory.client.InventoryClient;
import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_bff.inventory.service.InventoryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryClient client;

    public InventoryServiceImpl(InventoryClient client) {
        this.client = client;
    }

    @Override
    public List<InventoryResponseDTO> getAll(String category) {
        return client.getAll(category);
    }

    @Override
    public InventoryResponseDTO save(InventoryCreateDTO dto) {
        return client.save(dto);
    }

    @Override
    public InventoryResponseDTO getByCode(String code) {
        return client.getByCode(code);
    }

    @Override
    public InventoryResponseDTO update(String code, InventoryUpdateDTO dto) {
        return client.update(code, dto);
    }

    @Override
    public void deleteByCode(String code) {
        client.deleteByCode(code);
    }
}