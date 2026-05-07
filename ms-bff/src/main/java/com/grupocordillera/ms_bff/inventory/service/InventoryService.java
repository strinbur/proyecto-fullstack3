package com.grupocordillera.ms_bff.inventory.service;

import com.grupocordillera.ms_bff.inventory.client.InventoryClient;
import com.grupocordillera.ms_bff.inventory.dto.InventoryDTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryClient client;

    public InventoryService(InventoryClient client) {
        this.client = client;
    }

    public List<InventoryDTO> listar(String categoria) {
        return client.listar(categoria);
    }

    public InventoryDTO crear(InventoryDTO dto) {
        return client.crear(dto);
    }

    public InventoryDTO obtenerPorCodigo(String codigo) {
        return client.obtenerPorCodigo(codigo);
    }

    public InventoryDTO actualizar(String codigo, InventoryDTO dto) {
        return client.actualizar(codigo, dto);
    }

    public void eliminar(String codigo) {
        client.eliminar(codigo);
    }
}