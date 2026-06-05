package com.grupocordillera.ms_inventory.factory;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.model.Inventory;

import java.util.HashMap;

public class InventoryFactory {


    public static Inventory createInventory(InventoryCreateDTO data) {

        Inventory i = new Inventory();

        i.setCode(data.getCode());
        i.setName(data.getName());
        i.setBrand(data.getBrand());
        i.setPrice(data.getPrice());
        i.setQuantity(data.getQuantity());
        i.setCategory(data.getCategory());

        if (data.getSpecs() == null) {
            i.setSpecs(new HashMap<>());
        } else {
            i.setSpecs(new HashMap<>(data.getSpecs()));
        }

        // REGLAS DE NEGOCIO
        if ("tecnologia".equalsIgnoreCase(data.getCategory())) {
            i.getSpecs().put("garantia", "12 meses");
        }

        if (data.getQuantity() == 0) {
            i.getSpecs().put("estado", "sin stock");
        }

        return i;
    }


    public static Inventory updateInventory(
            Inventory existing,
            InventoryUpdateDTO data
    ) {

        existing.setName(data.getName());
        existing.setBrand(data.getBrand());
        existing.setPrice(data.getPrice());
        existing.setQuantity(data.getQuantity());
        existing.setCategory(data.getCategory());

        if (data.getSpecs() == null) {
            existing.setSpecs(new HashMap<>());
        } else {
            existing.setSpecs(new HashMap<>(data.getSpecs()));
        }

        // Si la categoría es tecnología, se asegura de que tenga la garantia de 12 meses
        if ("tecnologia".equalsIgnoreCase(data.getCategory())) {
            existing.getSpecs().put("garantia", "12 meses");
        }

        if (data.getQuantity() == 0) {
            existing.getSpecs().put("estado", "sin stock");
        }

        return existing;
    }


    public static InventoryResponseDTO toResponse(Inventory inventory) {

        InventoryResponseDTO dto = new InventoryResponseDTO();

        dto.setId(inventory.getId());
        dto.setCode(inventory.getCode());
        dto.setName(inventory.getName());
        dto.setBrand(inventory.getBrand());
        dto.setPrice(inventory.getPrice());
        dto.setQuantity(inventory.getQuantity());
        dto.setCategory(inventory.getCategory());
        dto.setSpecs(inventory.getSpecs());

        return dto;
    }
}