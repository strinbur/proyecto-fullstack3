package com.grupocordillera.ms_inventory.repository;

import com.grupocordillera.ms_inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

    Optional<Inventory> findByCode(String code);

    boolean existsByCode(String code);

    void deleteByCode(String code);

    List<Inventory> findByCategoryIgnoreCase(String category);
}