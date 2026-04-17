package com.example.portafolio_backend.config;


import com.example.portafolio_backend.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {
    private final JwtAuthenticationFilter filter;

    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {

                    // 🔓 PUBLICOS
                    authConfig.requestMatchers("/auth/signIn").permitAll();
                    authConfig.requestMatchers("/auth/signUp").permitAll();
                    authConfig.requestMatchers("/auth/google").permitAll();
                    authConfig.requestMatchers("/auth/forgot-password").permitAll();
                    authConfig.requestMatchers("/auth/reset-password").permitAll();


                            // 🔐 LOGOUT
                    authConfig.requestMatchers("/auth/signOut").authenticated();

                            // 📄 SWAGGER
                    authConfig.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll();

                            // 🧪 HEALTH
                    authConfig.requestMatchers("/ping").permitAll();

                            // 🍰 CAKES
                    authConfig.requestMatchers(HttpMethod.GET, "/cakes/v1/api").authenticated();
                    authConfig.requestMatchers(HttpMethod.POST, "/cakes/v1/api").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/cakes/v1/api/*").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/cakes/v1/api/*").hasRole("ADMIN");

                            // 🏷️ BRANDS
                    authConfig.requestMatchers("/brands/**").hasRole("ADMIN");

                            // 🧾 ORDERS (solo clientes)
                    authConfig.requestMatchers(HttpMethod.POST, "/orders/v1/api").hasRole("CUSTOMER");
                    authConfig.requestMatchers(HttpMethod.GET, "/orders/v1/api/customer/**").hasRole("CUSTOMER");
                    authConfig.requestMatchers(HttpMethod.GET, "/orders/*/pdf").hasRole("CUSTOMER");

                            // 👤 CUSTOMERS
                    authConfig.requestMatchers("/customers/**").hasAnyRole("ADMIN", "CUSTOMER");

                            // 🔒 TODO LO DEMÁS BLOQUEADO
                    authConfig.anyRequest().denyAll();
                });

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedOrigin));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
