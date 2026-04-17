package com.example.portafolio_backend.config.filter;

import com.example.portafolio_backend.persistance.entity.CustomerEntity;
import com.example.portafolio_backend.service.interfaces.ICustomerService;
import com.example.portafolio_backend.service.interfaces.IJwtService;
import com.example.portafolio_backend.service.interfaces.IJwtTokenCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtTokenCacheService cacheService;
    private final ICustomerService customerService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];


        // Usamos el cache service
        try {
            CustomerEntity entity = cacheService.getCustomerFromToken(jwt);

            HashSet<SimpleGrantedAuthority> rolesAuthorities = new HashSet<>();
            rolesAuthorities.add(new SimpleGrantedAuthority("ROLE_" + entity.getRole()));


            Authentication auth = new UsernamePasswordAuthenticationToken(entity.getEmail(), null, rolesAuthorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);

    }

}
