package com.example.rockpaperscissorsultimate.utils.dtos;

import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import lombok.Data;

@Data
public class RegisterMoveRequest {
    private String lobbyId;
    private PlayerChoice firstPlayerChoice;
    private PlayerChoice secondPlayerChoice;
}
