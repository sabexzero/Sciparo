package com.example.rockpaperscissorsultimate.utils.dtos;

import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegisterGameResultRequest {
    private UUID winnerId;
    private UUID loserId;
    private Long bet;
    private PlayerChoice winnerChoice;
    private PlayerChoice loserChoice;
}
