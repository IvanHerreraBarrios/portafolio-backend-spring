package com.example.portafolio_backend.service.interfaces;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;

public interface IJwtTokenCacheService {
    public void addToWhitelist(String token, CustomerEntity customer);

    public void addToBlacklist(String token);

    public void removeFromWhitelist(String token);

    public boolean isBlacklisted(String token);

    public CustomerEntity getCustomerFromToken(String token);
}
