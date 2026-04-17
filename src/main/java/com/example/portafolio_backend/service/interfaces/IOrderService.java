package com.example.portafolio_backend.service.interfaces;

import com.example.portafolio_backend.persistance.entity.OrderEntity;

import java.util.List;

public interface IOrderService {
    List<OrderEntity> findAll();

    OrderEntity findById(Long id);


    OrderEntity save(OrderEntity entity);

    List<OrderEntity> findAllByCustomerId(Long id);

    void delete(Long id);
}
