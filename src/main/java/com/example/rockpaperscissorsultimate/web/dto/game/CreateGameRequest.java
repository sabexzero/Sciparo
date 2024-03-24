package com.example.rockpaperscissorsultimate.web.dto.game;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Create game request DTO")
public record CreateGameRequest(
        @NotNull
        String creatorId,
        
        @Max(value = GameUtils.MAX_BET, message = "Bet must be at most 1,000,000")
        @Min(value = GameUtils.MIN_BET, message = "Bet must be at least 5")
        Integer bet
) {}
