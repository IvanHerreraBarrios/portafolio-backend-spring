package com.example.portafolio_backend.service;

import com.example.portafolio_backend.domain.dto.exceptions.CakeNotFoundException;
import com.example.portafolio_backend.persistance.entity.CakeEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICakeRepository;
import com.example.portafolio_backend.service.interfaces.ICakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CakeService implements ICakeService {

    private final ICakeRepository repository;

    @Override
    public List<CakeEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CakeEntity findById(Long id) {
        return repository.findById(id).orElseThrow(CakeNotFoundException::new);
    }

    @Override
    public CakeEntity save(CakeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public CakeEntity update(CakeEntity entity, Long id) {
        return repository.findById(id).map(
                cake -> {
                    cake.setStock(entity.getStock());
                    cake.setFlavor(entity.getFlavor());
                    cake.setImage(entity.getImage());
                    cake.setName(entity.getName());
                    cake.setPrice(entity.getPrice());
                    cake.setIdBrand(entity.getIdBrand());
                    return repository.save(cake);
                }
        ).orElseThrow(CakeNotFoundException::new);
    }


    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
