package com.example.rockpaperscissorsultimate.web.dto.game;

import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import com.example.rockpaperscissorsultimate.domain.game.PlayerChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Request to register game result")
public record RegisterGameResultRequest (
        @NotNull
        String gameId,
        @NotNull
        GameResult gameResult
) {}
