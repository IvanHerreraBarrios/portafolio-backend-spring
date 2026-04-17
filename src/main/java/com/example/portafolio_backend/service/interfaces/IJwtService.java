package com.example.portafolio_backend.service.interfaces;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;

import java.util.Map;

public interface IJwtService {

    public String extractUsername(String jwt);

    public String generateToken(CustomerEntity entity,  Map<String, Object> extraClaims);


}
