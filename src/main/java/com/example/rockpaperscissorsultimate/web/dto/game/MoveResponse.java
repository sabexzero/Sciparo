package com.example.rockpaperscissorsultimate.web.dto.game;

import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveResponse {
    private GameResult moveResult;
    private boolean isLastMove;
    private Game game;
}
