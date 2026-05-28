package com.grupocordillera.ms_cart.repository;

import com.grupocordillera.ms_cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository
        extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserEmail(String userEmail);
}