package com.example.portafolio_backend.persistance.repository.interfaces;


import com.example.portafolio_backend.persistance.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    List<OrderEntity> findAll();

    List<OrderEntity> findAllByCustomerId(Long customerId);

    Optional<OrderEntity> findById(Long id);


    OrderEntity save(OrderEntity entity);

    void delete(Long id);
}
