package com.grupocordillera.ms_bff.inventory.service.impl;

import com.grupocordillera.ms_bff.inventory.client.InventoryClient;
import com.grupocordillera.ms_bff.inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_bff.inventory.dto.InventoryUpdateDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class InventoryServiceImplTest {

    private final InventoryClient client =
            Mockito.mock(InventoryClient.class);

    private final InventoryServiceImpl service =
            new InventoryServiceImpl(client);

    @Test
    void shouldGetAll() {

        List<InventoryResponseDTO> products =
                List.of(new InventoryResponseDTO());

        Mockito.when(client.getAll(null))
                .thenReturn(products);

        List<InventoryResponseDTO> response =
                service.getAll(null);

        assertNotNull(response);
    }

    @Test
    void shouldSave() {

        InventoryCreateDTO dto =
                new InventoryCreateDTO();

        InventoryResponseDTO responseDto =
                new InventoryResponseDTO();

        Mockito.when(client.save(dto))
                .thenReturn(responseDto);

        InventoryResponseDTO response =
                service.save(dto);

        assertNotNull(response);
    }

    @Test
    void shouldGetByCode() {

        InventoryResponseDTO dto =
                new InventoryResponseDTO();

        Mockito.when(client.getByCode("P001"))
                .thenReturn(dto);

        InventoryResponseDTO response =
                service.getByCode("P001");

        assertNotNull(response);
    }

    @Test
    void shouldUpdate() {

        InventoryUpdateDTO dto =
                new InventoryUpdateDTO();

        InventoryResponseDTO responseDto =
                new InventoryResponseDTO();

        Mockito.when(client.update("P001", dto))
                .thenReturn(responseDto);

        InventoryResponseDTO response =
                service.update("P001", dto);

        assertNotNull(response);
    }

    @Test
    void shouldDeleteByCode() {

        service.deleteByCode("P001");

        Mockito.verify(client)
                .deleteByCode("P001");
    }
}