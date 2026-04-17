package com.example.portafolio_backend.persistance.crud;


import com.example.portafolio_backend.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderCrud extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByCustomerId(Long id);
}
