package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.web.dto.security.JwtRequest;
import com.example.rockpaperscissorsultimate.web.dto.security.JwtResponse;
import com.example.rockpaperscissorsultimate.web.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final PlayerService playerService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public JwtResponse login(
            final JwtRequest loginRequest
    ) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );
        Player player = playerService.getPlayerByName(loginRequest.getUsername());
        jwtResponse.setId(player.getId());
        jwtResponse.setUsername(player.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                player.getId(), player.getUsername(), player.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                player.getId(), player.getUsername())
        );
        return jwtResponse;
    }
    
    public JwtResponse refresh(
            final String refreshToken
    ) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
    
}
