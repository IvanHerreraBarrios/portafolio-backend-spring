package com.example.portafolio_backend.persistance.crud;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.persistance.entity.PasswordResetTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    Optional<PasswordResetTokenEntity> findByToken(String token);
    // Cambiado de deleteByUser a deleteAllByUser
    @Transactional
    int deleteAllByUser(CustomerEntity user);
}
