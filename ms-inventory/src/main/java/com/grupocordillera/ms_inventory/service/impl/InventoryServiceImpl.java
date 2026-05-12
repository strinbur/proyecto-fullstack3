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
    public List<InventoryResponseDTO> obtenerTodos() {

        return repository.findAll()
                .stream()
                .map(InventoryFactory::toResponse)
                .toList();
    }

    @Override
    public InventoryResponseDTO guardar(InventoryCreateDTO inventario) {

        Inventory nuevo = InventoryFactory.createInventory(inventario);

        if (repository.existsByCodigo(nuevo.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error, el codigo del producto ya existe"
            );
        }

        Inventory guardado = repository.save(nuevo);

        return InventoryFactory.toResponse(guardado);
    }

    @Override
    public InventoryResponseDTO obtenerPorCodigo(String codigo) {

        Inventory inventory = repository.findByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error, producto no encontrado"
                ));

        return InventoryFactory.toResponse(inventory);
    }

    @Override
    public void eliminarPorCodigo(String codigo) {

        if (!repository.existsByCodigo(codigo)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Error, producto no encontrado"
            );
        }

        repository.deleteByCodigo(codigo);
    }

    @Override
    public InventoryResponseDTO actualizar(
            String codigo,
            InventoryUpdateDTO nuevo
    ) {

        Inventory actualizado = repository.findByCodigo(codigo)
                .map(existing -> {

                    Inventory inventory =
                            InventoryFactory.updateInventory(existing, nuevo);

                    return repository.save(inventory);

                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error, producto no encontrado"
                ));

        return InventoryFactory.toResponse(actualizado);
    }

    @Override
    public List<InventoryResponseDTO> obtenerPorCategoria(String categoria) {

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error, la categoria no puede estar vacia"
            );
        }

        return repository.findByCategoriaIgnoreCase(categoria)
                .stream()
                .map(InventoryFactory::toResponse)
                .toList();
    }
}