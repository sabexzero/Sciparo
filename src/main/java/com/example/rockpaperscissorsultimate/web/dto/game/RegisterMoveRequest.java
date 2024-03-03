package com.example.rockpaperscissorsultimate.web.dto.game;

import lombok.Data;

@Data
public class RegisterMoveRequest {
    private String lobbyId;
    private String firstPlayerChoice;
    private String secondPlayerChoice;
}
