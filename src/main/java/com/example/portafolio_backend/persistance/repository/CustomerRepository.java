package com.example.portafolio_backend.persistance.repository;

import com.example.portafolio_backend.persistance.crud.ICustomerCrud;
import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerRepository implements ICustomerRepository {

    private final ICustomerCrud crud;


    @Override
    public List<CustomerEntity> findAll() {
        return crud.findAll();
    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {

        return crud.findById(id);
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        return crud.findByEmail(email);
    }

    @Override
    public CustomerEntity save(CustomerEntity entity) {
        return crud.save(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return crud.existsByEmail(email);
    }

    @Override
    public void delete(Long id) {
        crud.deleteById(id);
    }
}
