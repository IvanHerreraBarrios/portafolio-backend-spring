package com.example.portafolio_backend.controller;

import com.example.portafolio_backend.controller.mapper.ICustomerMapper;
import com.example.portafolio_backend.domain.dto.request.CustomerRequest;
import com.example.portafolio_backend.domain.dto.request.GoogleLoginRequest;
import com.example.portafolio_backend.domain.dto.request.LoginRequest;
import com.example.portafolio_backend.domain.dto.response.CustomerResponse;
import com.example.portafolio_backend.domain.dto.response.LoginResponse;
import com.example.portafolio_backend.service.PasswordResetService;
import com.example.portafolio_backend.service.interfaces.IAuthService;
import com.example.portafolio_backend.service.interfaces.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;
    private final ICustomerService customerService;
    private final ICustomerMapper customerMapper;
    private final PasswordResetService passwordResetService;

    @PostMapping("/signIn")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.singIn(request));
    }

    @PostMapping("/signUp")
    public ResponseEntity<CustomerResponse> signUp(@Valid @RequestBody CustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toCustomerResponse(customerService.save(customerMapper.toCustomerEntity(request))));
    }

    // 🔹 Nuevo endpoint signOut
    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@RequestHeader("Authorization") String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String token = authHeader.substring(7); // quitar "Bearer "
        authService.signOut(token);

        return ResponseEntity.noContent().build(); // 204 OK sin cuerpo
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        passwordResetService.sendResetEmail(body.get("email"));
        return ResponseEntity.ok(Map.of("message", "Email enviado"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        passwordResetService.resetPassword(body.get("token"), body.get("password"));
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        return ResponseEntity.ok(authService.googleLogin(request));
    }


}
