package com.example.portafolio_backend.service;

import com.example.portafolio_backend.domain.dto.exceptions.CustomerNotFoundException;
import com.example.portafolio_backend.domain.dto.response.CustomerUpdateResponse;
import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICustomerRepository;
import com.example.portafolio_backend.service.interfaces.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public List<CustomerEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CustomerEntity findById(Long id) {
        return repository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public CustomerEntity save(CustomerEntity entity) {
        if(repository.existsByEmail(entity.getEmail())){
            throw new IllegalArgumentException("The username is already registered.");
        }

        entity.setRole("CUSTOMER");
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

    @Override
    public CustomerUpdateResponse update(CustomerEntity entity, Long id) {
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        existing.setName(entity.getName());
        existing.setEmail(entity.getEmail());
        existing.setAddress(entity.getAddress());
        existing.setNumberCellphone(entity.getNumberCellphone());

        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(entity.getPassword()));
        }

        CustomerEntity updated = repository.save(existing);

        // 🔥 NUEVO JWT DESPUÉS DEL UPDATE
        String jwt = jwtService.generateToken(updated, generateClaims(updated));

        return CustomerUpdateResponse.builder()
                .jwt(jwt)
                .message("Customer updated successfully")
                .build();
    }

    private Map<String, Object> generateClaims(CustomerEntity customer) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("CustomerId", customer.getId());
        extraClaims.put("name", customer.getName());
        extraClaims.put("Rol", customer.getRole());
        return extraClaims;
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
