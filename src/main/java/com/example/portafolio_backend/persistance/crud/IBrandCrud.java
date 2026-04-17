package com.example.portafolio_backend.persistance.crud;

import com.example.portafolio_backend.persistance.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandCrud extends JpaRepository<BrandEntity, Long> {
}
