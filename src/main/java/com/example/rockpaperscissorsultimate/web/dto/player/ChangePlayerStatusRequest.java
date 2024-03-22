package com.example.rockpaperscissorsultimate.web.dto.player;

import com.example.rockpaperscissorsultimate.domain.player.PlayerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Move response DTO")
public record ChangePlayerStatusRequest (
        @NotNull
        String playerId,
        @NotNull
        PlayerStatus status
) {}
