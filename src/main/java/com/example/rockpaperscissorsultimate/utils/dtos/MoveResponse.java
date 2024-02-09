package com.example.rockpaperscissorsultimate.utils.dtos;

import com.example.rockpaperscissorsultimate.models.Game;
import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveResponse {
    private GameResult moveResult;
    private boolean isLastMove;
    private Game game;
}
