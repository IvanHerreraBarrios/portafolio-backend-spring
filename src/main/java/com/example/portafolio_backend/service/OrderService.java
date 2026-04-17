package com.example.portafolio_backend.service;

import com.example.portafolio_backend.domain.dto.exceptions.OrderNotFoundException;
import com.example.portafolio_backend.persistance.entity.CakeEntity;
import com.example.portafolio_backend.persistance.entity.OrderEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICakeRepository;
import com.example.portafolio_backend.persistance.repository.interfaces.IOrderRepository;
import com.example.portafolio_backend.service.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository repository;
    private final ICakeRepository cakeRepository;

    @Override
    public List<OrderEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public OrderEntity findById(Long id) {
        return repository.findById(id).orElseThrow(OrderNotFoundException::new);
    }



    @Override
    public OrderEntity save(OrderEntity entity) {
        entity.setStatus("success");
        entity.setCreationDate(LocalDateTime.now());
        entity.getOrderDetails().forEach(
                orderDetail -> {
                    CakeEntity cake = cakeRepository.findById(orderDetail.getId().getIdCake()).get();
                    cake.setStock( cake.getStock() - orderDetail.getQuantity());
                    cakeRepository.save(cake);
                }
        );

        return repository.save(entity);
    }



    @Override
    public List<OrderEntity> findAllByCustomerId(Long id) {
        return repository.findAllByCustomerId(id);
    }


    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
