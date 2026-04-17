package com.example.portafolio_backend.persistance.repository.interfaces;



import com.example.portafolio_backend.persistance.entity.CakeEntity;

import java.util.List;
import java.util.Optional;

public interface ICakeRepository {
    List<CakeEntity> findAll();

    Optional<CakeEntity> findById(Long id);

    CakeEntity save(CakeEntity entity);

    void delete(Long id);
}
