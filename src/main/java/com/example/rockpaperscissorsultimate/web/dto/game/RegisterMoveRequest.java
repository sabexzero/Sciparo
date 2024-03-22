package com.example.rockpaperscissorsultimate.web.dto.game;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Request to register move")
public record RegisterMoveRequest (
        @NotNull
        String gameId,
        @NotNull
        String firstPlayerChoice,
        @NotNull
        String secondPlayerChoice
) {}
