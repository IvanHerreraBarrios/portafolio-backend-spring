package com.example.portafolio_backend.service;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.service.interfaces.ICustomerService;
import com.example.portafolio_backend.service.interfaces.IJwtService;
import com.example.portafolio_backend.service.interfaces.IJwtTokenCacheService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class JwtTokenCacheService implements IJwtTokenCacheService {

    private final ICustomerService customerService;
    private final IJwtService jwtService;


    private final Map<String, CustomerEntity> whitelist = new ConcurrentHashMap<>();
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    @Override
    public void addToWhitelist(String token, CustomerEntity customer) {
        whitelist.put(token, customer);
    }

    @Override
    public void addToBlacklist(String token) {
        whitelist.remove(token); // Asegurarse que no esté en la blanca
        blacklist.add(token);
    }

    @Override
    public void removeFromWhitelist(String token) {
        whitelist.remove(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    @Override
    public CustomerEntity getCustomerFromToken(String token) {
        // Primero verifica la lista negra
        if (isBlacklisted(token)) {
            throw new RuntimeException("Blacklisted token");
        }

        // Si está en la lista blanca, devuelve directamente
        if (whitelist.containsKey(token)) {
            return whitelist.get(token);
        }

        // Si no está, busca en DB
        String username = jwtService.extractUsername(token); // EXTRAER USERNAME
        CustomerEntity customer = customerService.findByEmail(username); // Traer de DB
        if (customer != null) {
            whitelist.put(token, customer); // cachea para la próxima
        }
        return customer;
    }

    // 🔹 Método programado para resetear listas cada 12 horas
    //@Scheduled(fixedRate = 12 * 60 * 60 * 1000) // cada 12 horas
    @Scheduled(fixedDelay = 10 * 60 * 1000) // 10 minutos
    public void resetAllTokens() {
        // 🔹 Limpiar whitelist (solo expirados)
        whitelist.entrySet().removeIf(entry -> isTokenExpired(entry.getKey()));

        // 🔹 Limpiar blacklist (solo expirados)
        blacklist.removeIf(this::isTokenExpired);
    }

    private boolean isTokenExpired(String token) {
        try {
            jwtService.extractUsername(token); // valida firma y expiración
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            // cualquier token inválido también lo limpiamos
            return true;
        }
    }
}
