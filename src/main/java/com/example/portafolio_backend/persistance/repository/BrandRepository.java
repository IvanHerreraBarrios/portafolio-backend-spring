package com.example.portafolio_backend.persistance.repository;

import com.example.portafolio_backend.persistance.crud.IBrandCrud;
import com.example.portafolio_backend.persistance.entity.BrandEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrandRepository implements IBrandRepository {

    private final IBrandCrud crud;


    @Override
    public List<BrandEntity> findAll() {
        return crud.findAll();
    }

    @Override
    public Optional<BrandEntity> findById(Long id) {
        return crud.findById(id);
    }

    @Override
    public BrandEntity save(BrandEntity entity) {
        return crud.save(entity);
    }

    @Override
    public void delete(Long id) {
        crud.deleteById(id);
    }
}
