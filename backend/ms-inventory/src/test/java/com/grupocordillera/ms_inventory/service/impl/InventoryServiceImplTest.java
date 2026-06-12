package com.grupocordillera.ms_inventory.service.impl;

import com.grupocordillera.ms_inventory.dto.*;
import com.grupocordillera.ms_inventory.model.Inventory;
import com.grupocordillera.ms_inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private InventoryServiceImpl service;

    @Test
    void shouldReturnAllProducts() {

        when(repository.findAll())
                .thenReturn(List.of(new Inventory()));

        List<InventoryResponseDTO> result =
                service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldSaveProduct() {

        InventoryCreateDTO dto =
                new InventoryCreateDTO();

        dto.setCode("P001");

        Inventory inventory =
                new Inventory();

        inventory.setCode("P001");

        when(repository.existsByCode("P001"))
                .thenReturn(false);

        when(repository.save(any()))
                .thenReturn(inventory);

        InventoryResponseDTO result =
                service.save(dto);

        assertEquals("P001",
                result.getCode());
    }

    @Test
    void shouldThrowWhenCodeAlreadyExists() {

        InventoryCreateDTO dto =
                new InventoryCreateDTO();

        dto.setCode("P001");

        when(repository.existsByCode("P001"))
                .thenReturn(true);

        assertThrows(
                ResponseStatusException.class,
                () -> service.save(dto)
        );
    }

    @Test
    void shouldReturnProductByCode() {

        Inventory inventory =
                new Inventory();

        inventory.setCode("P001");

        when(repository.findByCode("P001"))
                .thenReturn(Optional.of(inventory));

        InventoryResponseDTO result =
                service.getByCode("P001");

        assertEquals("P001",
                result.getCode());
    }

    @Test
    void shouldThrowWhenProductNotFound() {

        when(repository.findByCode("P001"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> service.getByCode("P001")
        );
    }

    @Test
    void shouldDeleteProduct() {

        when(repository.existsByCode("P001"))
                .thenReturn(true);

        service.deleteByCode("P001");

        verify(repository)
                .deleteByCode("P001");
    }

    @Test
    void shouldThrowWhenDeleteProductNotFound() {

        when(repository.existsByCode("P001"))
                .thenReturn(false);

        assertThrows(
                ResponseStatusException.class,
                () -> service.deleteByCode("P001")
        );
    }

    @Test
    void shouldUpdateProduct() {

        Inventory existing =
                new Inventory();

        InventoryUpdateDTO dto =
                new InventoryUpdateDTO();

        dto.setName("Nuevo");

        when(repository.findByCode("P001"))
                .thenReturn(Optional.of(existing));

        when(repository.save(any()))
                .thenReturn(existing);

        InventoryResponseDTO result =
                service.update("P001", dto);

        assertNotNull(result);
    }

    @Test
    void shouldThrowWhenUpdateProductNotFound() {

        when(repository.findByCode("P001"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> service.update(
                        "P001",
                        new InventoryUpdateDTO()
                )
        );
    }

    @Test
    void shouldReturnProductsByCategory() {

        when(repository.findByCategoryIgnoreCase("Tecnologia"))
                .thenReturn(List.of(new Inventory()));

        List<InventoryResponseDTO> result =
                service.getByCategory("Tecnologia");

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowWhenCategoryIsEmpty() {

        assertThrows(
                ResponseStatusException.class,
                () -> service.getByCategory("")
        );
    }
}