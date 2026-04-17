package com.example.portafolio_backend.persistance.repository;

import com.example.portafolio_backend.persistance.crud.IOrderCrud;
import com.example.portafolio_backend.persistance.entity.OrderEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepository implements IOrderRepository {


    private final IOrderCrud crud;


    @Override
    public List<OrderEntity> findAll() {
        return crud.findAll();
    }

    @Override
    public List<OrderEntity> findAllByCustomerId(Long customerId) {
        return crud.findAllByCustomerId(customerId);
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return crud.findById(id);
    }


    @Override
    public OrderEntity save(OrderEntity entity) {

        entity.getOrderDetails().forEach(details -> {
            details.setOrder(entity);
        });
        return crud.save(entity);

    }

    @Override
    public void delete(Long id) {
        crud.deleteById(id);
    }
}
