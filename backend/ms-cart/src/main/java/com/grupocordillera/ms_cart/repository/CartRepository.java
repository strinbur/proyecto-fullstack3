package com.grupocordillera.ms_cart.repository;

import com.grupocordillera.ms_cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repositorio de MongoDB para acceder y persistir documentos de carrito.
 */
public interface CartRepository
        extends MongoRepository<Cart, String> {

    /**
     * Busca el carrito asociado al correo del usuario.
     *
     * @param userEmail correo del usuario autenticado
     * @return carrito encontrado, si existe
     */
    Optional<Cart> findByUserEmail(String userEmail);
}