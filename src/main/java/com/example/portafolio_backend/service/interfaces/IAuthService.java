package com.example.portafolio_backend.service.interfaces;

import com.example.portafolio_backend.domain.dto.request.GoogleLoginRequest;
import com.example.portafolio_backend.domain.dto.request.LoginRequest;
import com.example.portafolio_backend.domain.dto.response.LoginResponse;

public interface IAuthService {
    LoginResponse singIn(LoginRequest request);

    void signOut(String token);

     LoginResponse googleLogin(GoogleLoginRequest request);

}
