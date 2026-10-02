package com.app.backend.service;
import com.app.backend.dto.request.LoginRequest;
import com.app.backend.dto.request.RegisterRequest;
import com.app.backend.dto.response.AuthResponse;


public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);


    AuthResponse login(LoginRequest loginRequest);

}
