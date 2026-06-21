package com.grupocordillera.ms_order.repository;

import com.grupocordillera.ms_order.model.Order;
import com.grupocordillera.ms_order.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserEmail(String userEmail);

    List<Order> findByStatus(OrderStatus status);
}