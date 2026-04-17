package com.example.portafolio_backend.service.interfaces;


import com.example.portafolio_backend.persistance.entity.BrandEntity;

import java.util.List;


public interface IBrandService {
    List<BrandEntity> findAll();

    BrandEntity findById(Long id);

    BrandEntity save(BrandEntity entity);



    void delete(Long id);
}
