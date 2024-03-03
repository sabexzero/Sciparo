package com.example.rockpaperscissorsultimate.web.dto.player;

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
