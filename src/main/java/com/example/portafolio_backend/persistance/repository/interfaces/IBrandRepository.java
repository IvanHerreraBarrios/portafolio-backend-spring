package com.example.portafolio_backend.persistance.repository.interfaces;


import com.example.portafolio_backend.persistance.entity.BrandEntity;


import java.util.List;
import java.util.Optional;

public interface IBrandRepository {
    List<BrandEntity> findAll();

    Optional<BrandEntity> findById(Long id);

    BrandEntity save(BrandEntity entity);

    void delete(Long id);
}
