package com.example.portafolio_backend.persistance.crud;

import com.example.portafolio_backend.persistance.entity.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICakeCrud extends JpaRepository<CakeEntity, Long> {
}
