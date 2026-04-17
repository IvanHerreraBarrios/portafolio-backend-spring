package com.example.portafolio_backend.service;

import com.example.portafolio_backend.domain.dto.exceptions.CustomerNotFoundException;
import com.example.portafolio_backend.domain.dto.request.GoogleLoginRequest;
import com.example.portafolio_backend.domain.dto.request.LoginRequest;
import com.example.portafolio_backend.domain.dto.response.LoginResponse;
import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.persistance.repository.interfaces.ICustomerRepository;
import com.example.portafolio_backend.service.interfaces.IAuthService;
import com.example.portafolio_backend.service.interfaces.IJwtService;
import com.example.portafolio_backend.service.interfaces.IJwtTokenCacheService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final ICustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final IJwtTokenCacheService cacheService;

    @Value("${auth.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${auth.google.issuer}")
    private String GOOGLE_ISSUER;


    @Override
    public LoginResponse singIn(LoginRequest request) {
        CustomerEntity customer = repository.findByEmail(request.getEmail()).orElseThrow(CustomerNotFoundException::new);
        if(!passwordEncoder.matches(request.getPassword(), customer.getPassword())){
            throw new CustomerNotFoundException();
        }
        String jwt = jwtService.generateToken(customer, generateClaims(customer));

        return LoginResponse.builder().jwt(jwt).build();
    }

    private Map<String, Object> generateClaims(CustomerEntity customer) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("CustomerId", customer.getId());
        extraClaims.put("name", customer.getName());
        extraClaims.put("Rol", customer.getRole());
        return extraClaims;
    }

    public LoginResponse googleLogin(GoogleLoginRequest request) {
        GoogleIdToken.Payload payload = verifyGoogleToken(request.idToken());

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        CustomerEntity customer = repository.findByEmail(email)
                .orElseGet(() -> {
                    CustomerEntity newUser = new CustomerEntity();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setRole("CUSTOMER");
                    newUser.setPassword(passwordEncoder.encode("GOOGLE_USER"));
                    return repository.save(newUser);
                });

        String jwt = jwtService.generateToken(customer, generateClaims(customer));

        return LoginResponse.builder().jwt(jwt).build();
    }

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .setIssuer(GOOGLE_ISSUER)
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                throw new RuntimeException("Google invalid token");
            }

            return idToken.getPayload();

        } catch (Exception e) {
            throw new RuntimeException("Error validating Google token", e);
        }
    }

    @Override
    public void signOut(String token) {
        cacheService.addToBlacklist(token);
    }
}
