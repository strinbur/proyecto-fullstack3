package com.grupocordillera.ms_inventory.factory;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.model.Inventory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFactoryTest {

    @Test
    void shouldCreateInventory() {

        InventoryCreateDTO dto =
                new InventoryCreateDTO();

        dto.setCode("P001");
        dto.setName("Mouse");
        dto.setBrand("Logitech");
        dto.setPrice(1000);
        dto.setQuantity(10);
        dto.setCategory("Tecnologia");

        Inventory inventory =
                InventoryFactory.createInventory(dto);

        assertEquals("P001",
                inventory.getCode());

        assertEquals(
                "12 meses",
                inventory.getSpecs()
                        .get("garantia")
        );
    }

    @Test
    void shouldCreateInventoryWithoutStock() {

        InventoryCreateDTO dto =
                new InventoryCreateDTO();

        dto.setQuantity(0);
        dto.setCategory("Tecnologia");

        Inventory inventory =
                InventoryFactory.createInventory(dto);

        assertEquals(
                "sin stock",
                inventory.getSpecs()
                        .get("estado")
        );
    }

    @Test
    void shouldUpdateInventory() {

        Inventory inventory =
                new Inventory();

        InventoryUpdateDTO dto =
                new InventoryUpdateDTO();

        dto.setName("Nuevo");
        dto.setCategory("Tecnologia");
        dto.setQuantity(5);

        Inventory result =
                InventoryFactory.updateInventory(
                        inventory,
                        dto
                );

        assertEquals(
                "Nuevo",
                result.getName()
        );
    }

    @Test
    void shouldConvertToResponseDTO() {

        Inventory inventory =
                new Inventory();

        inventory.setId("1");
        inventory.setCode("P001");

        InventoryResponseDTO dto =
                InventoryFactory.toResponse(
                        inventory
                );

        assertEquals("1", dto.getId());
        assertEquals("P001", dto.getCode());
    }
}