package com.grupocordillera.ms_bff.inventory.controller;

import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_bff.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class InventoryControllerTest {

    private final InventoryService service =
            Mockito.mock(InventoryService.class);

    private final InventoryController controller =
            new InventoryController(service);

    @Test
    void shouldGetAllProducts() {

        List<InventoryResponseDTO> products =
                List.of(new InventoryResponseDTO());

        Mockito.when(service.getAll(null))
                .thenReturn(products);

        List<InventoryResponseDTO> response =
                controller.getAll(null);

        assertNotNull(response);
    }

    @Test
    void shouldSaveProduct() {

        InventoryCreateDTO dto =
                new InventoryCreateDTO();

        dto.setCode("P001");

        InventoryResponseDTO responseDto =
                new InventoryResponseDTO();

        Mockito.when(service.save(dto))
                .thenReturn(responseDto);

        InventoryResponseDTO response =
                controller.save(dto);

        assertNotNull(response);
    }

    @Test
    void shouldGetProductByCode() {

        InventoryResponseDTO dto =
                new InventoryResponseDTO();

        Mockito.when(service.getByCode("P001"))
                .thenReturn(dto);

        InventoryResponseDTO response =
                controller.getByCode("P001");

        assertNotNull(response);
    }

    @Test
    void shouldUpdateProduct() {

        InventoryUpdateDTO dto =
                new InventoryUpdateDTO();

        InventoryResponseDTO responseDto =
                new InventoryResponseDTO();

        Mockito.when(service.update("P001", dto))
                .thenReturn(responseDto);

        InventoryResponseDTO response =
                controller.update("P001", dto);

        assertNotNull(response);
    }

    @Test
    void shouldDeleteProduct() {

        controller.deleteByCode("P001");

        Mockito.verify(service)
                .deleteByCode("P001");
    }
}