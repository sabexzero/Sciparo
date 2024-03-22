package com.example.rockpaperscissorsultimate.web.dto.game;

import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Move response DTO")
public record MoveResponse (
        @NotNull
        GameResult moveResult,
        @NotNull
        boolean isLastMove,
        @NotNull
        Game game
) {}
