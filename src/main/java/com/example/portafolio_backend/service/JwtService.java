package com.example.portafolio_backend.service;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.service.interfaces.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService implements IJwtService {

    @Value("${security.jwt.expiration.minutes}")
    private Long EXPIRATED_MINUTES;

    @Value("${security.jwt.secret.key}")
    private String SECRET_KEY;


    @Override
    public String extractUsername(String jwt) {
        return getClaims(jwt).getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
    }

    private Key generateKey() {
        byte[] secretAsBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBites);
    }


    @Override
    public String generateToken(CustomerEntity entity, Map<String, Object> extraClaims) {
        Date issueat = new Date(System.currentTimeMillis());
        Date expiration = new Date(issueat.getTime() + (EXPIRATED_MINUTES * 60 * 1000));
        //Date expiration = new Date(issueat.getTime() + (EXPIRATED_MINUTES * 60 * 1000));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(entity.getEmail())
                .setIssuedAt(issueat)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
