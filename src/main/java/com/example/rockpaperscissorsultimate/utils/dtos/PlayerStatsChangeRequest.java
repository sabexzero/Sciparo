package com.example.rockpaperscissorsultimate.utils.dtos;

import com.example.rockpaperscissorsultimate.models.Player;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PlayerStatsChangeRequest {
    private UUID playerId;
    private Long bet;
    private Boolean gameResult;
}
