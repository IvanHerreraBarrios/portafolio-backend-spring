package com.example.portafolio_backend.service.interfaces;


import com.example.portafolio_backend.persistance.entity.CakeEntity;

import java.util.List;

public interface ICakeService {
    List<CakeEntity> findAll();

    CakeEntity findById(Long id);

    CakeEntity save(CakeEntity entity);

    CakeEntity update(CakeEntity entity, Long id);


    void delete(Long id);
}
