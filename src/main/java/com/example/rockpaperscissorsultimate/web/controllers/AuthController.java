package com.example.rockpaperscissorsultimate.web.controllers;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.service.AuthService;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.web.dto.player.PlayerDto;
import com.example.rockpaperscissorsultimate.web.dto.player.SignUpPlayerRequest;
import com.example.rockpaperscissorsultimate.web.dto.security.JwtRequest;
import com.example.rockpaperscissorsultimate.web.dto.security.JwtResponse;
import com.example.rockpaperscissorsultimate.web.dto.validation.OnCreate;
import com.example.rockpaperscissorsultimate.web.mappers.impls.player.PlayerMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
        name = "Auth Controller",
        description = "Auth API"
)

//TODO: realize javadoc and add swagger info for auth-controller
//  labels: documentation

public class AuthController {
    
    private final AuthService authService;
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @PostMapping("/login")
    public JwtResponse login(
            @Validated @RequestBody final JwtRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }
    
    @PostMapping("/register")
    public PlayerDto register(
            @Validated(OnCreate.class)
            @RequestBody final SignUpPlayerRequest signUpPlayerRequest
    ) {
        Player createdplayer = playerService.createPlayer(signUpPlayerRequest);
        logger.info(String.format("Player created with name: %s and ID: %s was created!",createdplayer.getUsername(),createdplayer.getId()));
        return playerMapper.toDto(createdplayer);
    }
    
    @PostMapping("/refresh")
    public JwtResponse refresh(
            @RequestBody final String refreshToken
    ) {
        return authService.refresh(refreshToken);
    }
    
}