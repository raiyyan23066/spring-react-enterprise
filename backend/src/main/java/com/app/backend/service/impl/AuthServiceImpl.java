package com.app.backend.service.impl;
import com.app.backend.dto.request.LoginRequest;
import com.app.backend.dto.request.RegisterRequest;
import com.app.backend.dto.response.AuthResponse;
import com.app.backend.entity.User;
import com.app.backend.repository.UserRepository;
import com.app.backend.security.CustomUserDetails;
import com.app.backend.security.CustomUserDetailsService;
import com.app.backend.security.JwtTokenProvider;
import com.app.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @Override
    public AuthResponse register(RegisterRequest registerRequest){
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email Already Exist");
        }
        User user=User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .build();
        userRepository.save(user);

    Authentication authentication=authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    registerRequest.getEmail(),
                    registerRequest.getPassword()
            )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=tokenProvider.generateToken(authentication);

    return AuthResponse.builder()
            .token(jwt)
            .tokeType("Bearer")
            .id(user.getId())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .roles(user.getRoles())
            .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=tokenProvider.generateToken(authentication);


        CustomUserDetails userDetails=(CustomUserDetails) authentication.getPrincipal();
        Set<String>roles=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        User user=userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new IllegalArgumentException("User Not Found! "));

        return  AuthResponse.builder()
                .token(jwt)
                .tokeType("Bearer")
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .fullName(user.getFullName())
                .roles(roles)
                .build();
    }

}
