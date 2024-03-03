package com.example.rockpaperscissorsultimate.web.dto.player;

import com.example.rockpaperscissorsultimate.domain.player.PlayerStatus;
import lombok.Data;

@Data
public class ChangePlayerStatusRequest {
    private String playerId;
    private PlayerStatus status;
}
