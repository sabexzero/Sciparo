package com.example.rockpaperscissorsultimate.utils.dtos;

import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegisterGameResultRequest {
    
    @NotNull(message = "The winner's ID is a required field")
    private Long winnerId;
    
    @NotNull(message = "The loser's ID is a required field")
    private Long loserId;
    
    @Min(value = 5, message = "Bet must be at least 5")
    @Max(value = 1_000_000, message = "Bet must be at most 1,000,000")
    private Long bet;
    
    @NotNull(message = "The winner's choice is a required field")
    private PlayerChoice winnerChoice;
    
    @NotNull(message = "The loser's choice is a required field")
    private PlayerChoice loserChoice;
}
