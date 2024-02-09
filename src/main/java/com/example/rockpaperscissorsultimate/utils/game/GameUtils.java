package com.example.rockpaperscissorsultimate.utils.game;

import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameUtils {
    public static final int MAX_PLAYERS = 2;
    public static final int CARDS_AMOUNT = 6;
    public static final String GAME_START_MESSAGE = "Game started!";
    public static String getRoundResultString(int round, String moveResult){
        return String.format("Round %d : %s ",round, moveResult);
    }
    public static String getGameResultString(String gameResult){
        return String.format("Game end! : %s", gameResult);
    }
    public static GameResult determineRoundResult(PlayerChoice player1Choice, PlayerChoice player2Choice) {
        if (player1Choice == player2Choice) {
            return GameResult.DRAW;
        } else if ((player1Choice == PlayerChoice.ROCK && player2Choice == PlayerChoice.SCISSORS) ||
                (player1Choice == PlayerChoice.SCISSORS && player2Choice == PlayerChoice.PAPER) ||
                (player1Choice == PlayerChoice.PAPER && player2Choice == PlayerChoice.ROCK)) {
            return GameResult.PLAYER1_WON;
        } else {
            return GameResult.PLAYER2_WON;
        }
    }
}
