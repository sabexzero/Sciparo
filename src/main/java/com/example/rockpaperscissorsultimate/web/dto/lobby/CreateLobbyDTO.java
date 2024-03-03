package com.example.rockpaperscissorsultimate.web.dto.lobby;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLobbyDTO {
    private Player creator;
    private String title;
    private int bet;
}
