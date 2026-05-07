package com.grupocordillera.ms_inventory.repository;

import com.grupocordillera.ms_inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

    Optional<Inventory> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    void deleteByCodigo(String codigo);

    List<Inventory> findByCategoriaIgnoreCase(String categoria);
}