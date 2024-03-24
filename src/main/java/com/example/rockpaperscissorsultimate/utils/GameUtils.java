package com.example.rockpaperscissorsultimate.utils;

import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import com.example.rockpaperscissorsultimate.domain.game.GameStatus;
import com.example.rockpaperscissorsultimate.domain.game.PlayerChoice;
import com.example.rockpaperscissorsultimate.web.dto.game.MoveResponse;

/**
 * The class contains the necessary constants for the operation of GameService and GameController
 */
public class GameUtils {
    //Game Constants
    public static final int MAX_PLAYERS = 2;
    public static final int CARDS_AMOUNT = 6;
    public static final int MAX_BET = 1_000_000;
    public static final int MIN_BET = 5;
    public static final GameStatus INITIAL_GAME_STATUS = GameStatus.WAITING;
    public static final int DRAW_DELTA_ELO = -1;
    
    //WebSockets constants
    public static final String GAME_TOPIC = "/topic/game/";
    
    public static final String START_MESSAGE = "Game was started!";
    
    
    public static String getGameCreatedString(
            String creatorName,
            Integer bet
    ){
        return String.format("%s created a match with a bet of %d", creatorName, bet);
    }
    
    public static String getRoundResultString(
            Integer round,
            String moveResult){
        return String.format("Round %d : %s",round, moveResult);
    }
    
    public static String getGameResultString(String gameResult){
        return String.format("Game end! : %s", gameResult);
    }
    
    public static String getPlayerJoinString(String playerName){
        return String.format("%s joined the match", playerName);
    }
    
    public static String getPlayerLeaveString(String playerName){
        return String.format("%s left the match.", playerName);
    }
    
    public static String getMatchReadyStatusString(Integer readyPlayersNum){
        return String.format("Players ready for the match : %d/%d", readyPlayersNum, GameUtils.MAX_PLAYERS);
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
