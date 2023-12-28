package com.example.rockpaperscissorsultimate.utils.dtos;

import com.example.rockpaperscissorsultimate.models.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerStatsChangeRequest {
    private Player player;
    private Long bet;
    private Boolean gameResult;
}
