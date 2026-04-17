package com.example.portafolio_backend.persistance.repository.interfaces;



import com.example.portafolio_backend.persistance.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository {
    List<CustomerEntity> findAll();

    Optional<CustomerEntity> findById(Long id);

    Optional<CustomerEntity> findByEmail(String email);

    CustomerEntity save(CustomerEntity entity);

    public boolean existsByEmail(String email);

    void delete(Long id);
}
