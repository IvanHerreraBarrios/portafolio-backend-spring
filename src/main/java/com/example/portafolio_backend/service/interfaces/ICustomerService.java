package com.example.portafolio_backend.service.interfaces;


import com.example.portafolio_backend.domain.dto.response.CustomerUpdateResponse;
import com.example.portafolio_backend.persistance.entity.CustomerEntity;

import java.util.List;

public interface ICustomerService {
    List<CustomerEntity> findAll();

    CustomerEntity findById(Long id);

    CustomerEntity save(CustomerEntity entity);

    CustomerUpdateResponse update(CustomerEntity entity, Long id);

    CustomerEntity findByEmail(String email);
    void delete(Long id);
}
