package com.example.rockpaperscissorsultimate.utils;

import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import com.example.rockpaperscissorsultimate.domain.game.GameStatus;
import com.example.rockpaperscissorsultimate.domain.game.PlayerChoice;

public class GameUtils {
    public static final int MAX_PLAYERS = 2;
    public static final int CARDS_AMOUNT = 6;
    public static final int MAX_BET = 1_000_000;
    public static final int MIN_BET = 5;
    public static GameStatus INITIAL_GAME_STATUS = GameStatus.WAITING;
    public static final String GAME_START_MESSAGE = "Game started!";
    public static String getRoundResultString(int round, String moveResult){
        return String.format("Round %d : %s ",round, moveResult);
    }
    public static String getGameResultString(String gameResult){
        return String.format("Game end! : %s", gameResult);
    }
    public static GameResult determineRoundResult(String firstPlayerChoiceString, String secondPlayerChoiceString) {
        PlayerChoice firstPlayerChoice = PlayerChoice.valueOf(firstPlayerChoiceString);
        PlayerChoice secondPlayerChoice = PlayerChoice.valueOf(secondPlayerChoiceString);
        if (firstPlayerChoice == secondPlayerChoice) {
            return GameResult.DRAW;
        } else if ((firstPlayerChoice == PlayerChoice.ROCK && secondPlayerChoice == PlayerChoice.SCISSORS) ||
                (firstPlayerChoice == PlayerChoice.SCISSORS && secondPlayerChoice == PlayerChoice.PAPER) ||
                (firstPlayerChoice == PlayerChoice.PAPER && secondPlayerChoice == PlayerChoice.ROCK)) {
            return GameResult.PLAYER1_WON;
        } else {
            return GameResult.PLAYER2_WON;
        }
    }
}
