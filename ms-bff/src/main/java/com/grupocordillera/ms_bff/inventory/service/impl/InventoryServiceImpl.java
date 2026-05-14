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
    public List<InventoryResponseDTO> listar(String categoria) {
        return client.listar(categoria);
    }

    @Override
    public InventoryResponseDTO crear(InventoryCreateDTO dto) {
        return client.crear(dto);
    }

    @Override
    public InventoryResponseDTO obtenerPorCodigo(String codigo) {
        return client.obtenerPorCodigo(codigo);
    }

    @Override
    public InventoryResponseDTO actualizar(String codigo, InventoryUpdateDTO dto) {
        return client.actualizar(codigo, dto);
    }

    @Override
    public void eliminar(String codigo) {
        client.eliminar(codigo);
    }
}