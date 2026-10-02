package com.app.backend.controller;

import com.app.backend.dto.response.ApiResponse;
import com.app.backend.dto.response.AuthResponse;
import com.app.backend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>>getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ){
    Map<String,Object> userProfile=Map.of(
            "id",currentUser.getId(),
            "email",currentUser.getEmail(),
            "authorities",currentUser.getAuthorities()
    );
    return ResponseEntity.ok(ApiResponse.success("User profile retrieved successfully ",userProfile));
    }

}
