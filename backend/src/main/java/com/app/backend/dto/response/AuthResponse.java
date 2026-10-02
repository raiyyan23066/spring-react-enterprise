package com.app.backend.dto.response;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    @Builder.Default
    private String tokeType="Bearer";
    private Long id;
    private String email;
    private String fullName;
    private Set<String >roles;
}
