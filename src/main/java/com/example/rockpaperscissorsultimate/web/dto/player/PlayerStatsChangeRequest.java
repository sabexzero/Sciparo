package com.example.rockpaperscissorsultimate.web.dto.player;

import com.example.rockpaperscissorsultimate.utils.GameUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Request to change player stats after game")
public record PlayerStatsChangeRequest (
        @NotNull(message = "Player id field is required")
        String playerId,
        @NotNull
        @Max(value = GameUtils.MAX_BET, message = "Bet must be at most 1,000,000")
        @Min(value = GameUtils.MIN_BET, message = "Bet must be at least 5")
        Long bet,
        @NotNull
        Boolean gameResult
) {}
