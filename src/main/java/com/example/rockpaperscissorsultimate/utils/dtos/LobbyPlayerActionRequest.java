package com.example.rockpaperscissorsultimate.utils.dtos;

import lombok.Data;

@Data
public class LobbyPlayerActionRequest {
    private String lobbyId;
    private String playerId;
}
