package com.example.rockpaperscissorsultimate.web.dto.lobby;

import lombok.Data;

@Data
public class LobbyPlayerActionRequest {
    private String lobbyId;
    private String playerId;
}
