package com.grupocordillera.ms_inventory.service;

import com.grupocordillera.ms_inventory.model.Inventory;
import com.grupocordillera.ms_inventory.repository.InventoryRepository;
import com.grupocordillera.ms_inventory.factory.InventoryFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<Inventory> obtenerTodos() {
        return repository.findAll();
    }

    public Inventory guardar(Inventory inventario) {


        Inventory nuevo = InventoryFactory.crearInventario(inventario);

        if (repository.existsByCodigo(nuevo.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error, el codigo del producto ya existe"
            );
        }

        return repository.save(nuevo);
    }

    public Inventory obtenerPorCodigo(String codigo) {
        return repository.findByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error, producto no encontrado"
                ));
    }

    public void eliminarPorCodigo(String codigo) {

        if (!repository.existsByCodigo(codigo)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Error, producto no encontrado"
            );
        }

        repository.deleteByCodigo(codigo);
    }

    public Inventory actualizar(String codigo, Inventory nuevo) {

        return repository.findByCodigo(codigo).map(existing -> {

            Inventory actualizado = InventoryFactory.crearInventario(nuevo);

            actualizado.setId(existing.getId());
            actualizado.setCodigo(existing.getCodigo());

            return repository.save(actualizado);

        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Error, producto no encontrado"
        ));
    }

    public List<Inventory> obtenerPorCategoria(String categoria) {

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error, la categoria no puede estar vacia"
            );
        }

        return repository.findByCategoriaIgnoreCase(categoria);
    }
}