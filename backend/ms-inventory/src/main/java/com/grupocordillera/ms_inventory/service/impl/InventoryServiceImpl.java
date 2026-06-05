package com.grupocordillera.ms_inventory.service.impl;

import com.grupocordillera.ms_inventory.dto.InventoryCreateDTO;
import com.grupocordillera.ms_inventory.dto.InventoryResponseDTO;
import com.grupocordillera.ms_inventory.dto.InventoryUpdateDTO;
import com.grupocordillera.ms_inventory.factory.InventoryFactory;
import com.grupocordillera.ms_inventory.model.Inventory;
import com.grupocordillera.ms_inventory.repository.InventoryRepository;
import com.grupocordillera.ms_inventory.service.InventoryService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InventoryResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(InventoryFactory::toResponse)
                .toList();
    }

    @Override
    public InventoryResponseDTO save(InventoryCreateDTO inventoryDTO) {

        Inventory newInventory =
                InventoryFactory.createInventory(inventoryDTO);

        if (repository.existsByCode(newInventory.getCode())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error, el codigo del producto ya existe"
            );
        }

        Inventory savedInventory =
                repository.save(newInventory);

        return InventoryFactory.toResponse(savedInventory);
    }

    @Override
    public InventoryResponseDTO getByCode(String code) {

        Inventory inventory = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error, producto no encontrado"
                ));

        return InventoryFactory.toResponse(inventory);
    }

    @Override
    public void deleteByCode(String code) {

        if (!repository.existsByCode(code)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Error, producto no encontrado"
            );
        }

        repository.deleteByCode(code);
    }

    @Override
    public InventoryResponseDTO update(
            String code,
            InventoryUpdateDTO updatedData
    ) {

        Inventory updatedInventory = repository.findByCode(code)
                .map(existing -> {

                    Inventory inventory =
                            InventoryFactory.updateInventory(
                                    existing,
                                    updatedData
                            );

                    return repository.save(inventory);

                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error, producto no encontrado"
                ));

        return InventoryFactory.toResponse(updatedInventory);
    }

    @Override
    public List<InventoryResponseDTO> getByCategory(String category) {

        if (category == null || category.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error, la categoria no puede estar vacia"
            );
        }

        return repository.findByCategoryIgnoreCase(category)
                .stream()
                .map(InventoryFactory::toResponse)
                .toList();
    }
}