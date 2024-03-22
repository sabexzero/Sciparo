package com.example.rockpaperscissorsultimate.web.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO to request leave or join player to game")
public record PlayerInOutRequest(
        @NotNull
        String playerId,
        @NotNull
        String gameId
)
{ }
