package com.example.portafolio_backend.service;

import com.example.portafolio_backend.domain.dto.exceptions.BrandNotFoundEsception;

import com.example.portafolio_backend.persistance.entity.BrandEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.IBrandRepository;
import com.example.portafolio_backend.service.interfaces.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final IBrandRepository repository;

    @Override
    public List<BrandEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public BrandEntity findById(Long id) {
        return repository.findById(id).orElseThrow(BrandNotFoundEsception::new);
    }

    @Override
    public BrandEntity save(BrandEntity entity) {
        return repository.save(entity);
    }




    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
