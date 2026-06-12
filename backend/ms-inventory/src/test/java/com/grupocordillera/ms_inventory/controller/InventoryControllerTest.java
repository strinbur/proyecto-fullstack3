package com.grupocordillera.ms_inventory.controller;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService service;

    @InjectMocks
    private InventoryController controller;

    @Test
    void shouldGetAllProducts() {

        when(service.getAll())
                .thenReturn(List.of(new InventoryResponseDTO()));

        ResponseEntity<List<InventoryResponseDTO>> response =
                controller.getAll(null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());

        verify(service).getAll();
    }

    @Test
    void shouldGetProductsByCategory() {

        when(service.getByCategory("Tecnologia"))
                .thenReturn(List.of(new InventoryResponseDTO()));

        ResponseEntity<List<InventoryResponseDTO>> response =
                controller.getAll("Tecnologia");

        assertEquals(200, response.getStatusCode().value());

        verify(service).getByCategory("Tecnologia");
    }

    @Test
    void shouldCreateProduct() {

        InventoryCreateDTO dto = new InventoryCreateDTO();

        InventoryResponseDTO responseDTO =
                new InventoryResponseDTO();

        when(service.save(dto))
                .thenReturn(responseDTO);

        ResponseEntity<InventoryResponseDTO> response =
                controller.create(dto);

        assertEquals(201, response.getStatusCode().value());

        verify(service).save(dto);
    }

    @Test
    void shouldGetByCode() {

        InventoryResponseDTO dto =
                new InventoryResponseDTO();

        dto.setCode("P001");

        when(service.getByCode("P001"))
                .thenReturn(dto);

        ResponseEntity<InventoryResponseDTO> response =
                controller.getByCode("P001");

        assertEquals("P001",
                response.getBody().getCode());

        verify(service).getByCode("P001");
    }

    @Test
    void shouldDeleteProduct() {

        ResponseEntity<Void> response =
                controller.deleteByCode("P001");

        assertEquals(204,
                response.getStatusCode().value());

        verify(service).deleteByCode("P001");
    }

    @Test
    void shouldUpdateProduct() {

        InventoryUpdateDTO updateDTO =
                new InventoryUpdateDTO();

        InventoryResponseDTO responseDTO =
                new InventoryResponseDTO();

        when(service.update("P001", updateDTO))
                .thenReturn(responseDTO);

        ResponseEntity<InventoryResponseDTO> response =
                controller.update(
                        "P001",
                        updateDTO
                );

        assertEquals(200,
                response.getStatusCode().value());

        verify(service)
                .update("P001", updateDTO);
    }
}