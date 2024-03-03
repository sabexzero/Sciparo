package com.example.rockpaperscissorsultimate.web.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Request after login")
public class JwtResponse {
    private String id;
    private String username;
    private String accessToken;
    private String refreshToken;
}
