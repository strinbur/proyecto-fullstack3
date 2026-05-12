package com.grupocordillera.ms_inventory.factory;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.model.Inventory;

import java.util.HashMap;

public class InventoryFactory {

    // CREATE DTO -> MODEL
    public static Inventory createInventory(InventoryCreateDTO data) {

        Inventory i = new Inventory();

        i.setCodigo(data.getCodigo());
        i.setNombre(data.getNombre());
        i.setMarca(data.getMarca());
        i.setPrecio(data.getPrecio());
        i.setCantidad(data.getCantidad());
        i.setCategoria(data.getCategoria());

        if (data.getAtributos() == null) {
            i.setAtributos(new HashMap<>());
        } else {
            i.setAtributos(new HashMap<>(data.getAtributos()));
        }

        // REGLAS DE NEGOCIO
        if ("tecnologia".equalsIgnoreCase(data.getCategoria())) {
            i.getAtributos().put("garantia", "12 meses");
        }

        if (data.getCantidad() == 0) {
            i.getAtributos().put("estado", "sin stock");
        }

        return i;
    }

    // UPDATE DTO -> MODEL
    public static Inventory updateInventory(
            Inventory existing,
            InventoryUpdateDTO data
    ) {

        existing.setNombre(data.getNombre());
        existing.setMarca(data.getMarca());
        existing.setPrecio(data.getPrecio());
        existing.setCantidad(data.getCantidad());
        existing.setCategoria(data.getCategoria());

        if (data.getAtributos() == null) {
            existing.setAtributos(new HashMap<>());
        } else {
            existing.setAtributos(new HashMap<>(data.getAtributos()));
        }

        // REGLAS DE NEGOCIO
        if ("tecnologia".equalsIgnoreCase(data.getCategoria())) {
            existing.getAtributos().put("garantia", "12 meses");
        }

        if (data.getCantidad() == 0) {
            existing.getAtributos().put("estado", "sin stock");
        }

        return existing;
    }

    // MODEL -> RESPONSE DTO
    public static InventoryResponseDTO toResponse(Inventory inventory) {

        InventoryResponseDTO dto = new InventoryResponseDTO();

        dto.setId(inventory.getId());
        dto.setCodigo(inventory.getCodigo());
        dto.setNombre(inventory.getNombre());
        dto.setMarca(inventory.getMarca());
        dto.setPrecio(inventory.getPrecio());
        dto.setCantidad(inventory.getCantidad());
        dto.setCategoria(inventory.getCategoria());
        dto.setAtributos(inventory.getAtributos());

        return dto;
    }
}