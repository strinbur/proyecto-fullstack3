package com.grupocordillera.ms_order.repository;

import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repositorio para la entidad {@link Order} en MongoDB.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    /**
     * Busca órdenes por el correo electrónico del usuario.
     *
     * @param userEmail correo electrónico del usuario
     * @return lista de órdenes del usuario
     */
    List<Order> findByUserEmail(String userEmail);

    /**
     * Busca órdenes por su estado.
     *
     * @param status estado de la orden
     * @return lista de órdenes en ese estado
     */
    List<Order> findByStatus(OrderStatus status);
} 