package com.app.backend.controller;

import com.app.backend.dto.response.AuthResponse;
import com.app.backend.dto.response.ApiResponse;
import com.app.backend.dto.request.LoginRequest;
import com.app.backend.dto.request.RegisterRequest;
import com.app.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest){
        AuthResponse response=authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registed Successfully " , response));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>>login(@Valid @RequestBody LoginRequest loginRequest){
        AuthResponse response=authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("User logged in Successfully " , response));
    }
}
