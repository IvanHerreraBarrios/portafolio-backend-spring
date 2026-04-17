package com.example.portafolio_backend.persistance.repository;


import com.example.portafolio_backend.persistance.crud.ICakeCrud;
import com.example.portafolio_backend.persistance.entity.CakeEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CakeRepository implements ICakeRepository {


    private final ICakeCrud crud;


    @Override
    public List<CakeEntity> findAll() {
        return crud.findAll();
    }

    @Override
    public Optional<CakeEntity> findById(Long id) {
        return crud.findById(id);
    }

    @Override
    public CakeEntity save(CakeEntity entity) {
        return crud.save(entity);
    }

    @Override
    public void delete(Long id) {
        crud.deleteById(id);
    }
}
