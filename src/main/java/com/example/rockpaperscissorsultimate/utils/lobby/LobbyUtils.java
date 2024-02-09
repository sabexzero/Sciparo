package com.example.rockpaperscissorsultimate.utils.lobby;

public class LobbyUtils {
    public static final String ALL_PLAYERS_READY_MESSAGE = "All players are ready, the lobby has been formed!";
    public static String playerJoinMessage(String playerId){
        return "Player with ID: " + playerId + "joined to lobby";
    }
    
    public static String playerLeaveMessage(String playerId){
        return "Player with ID: " + playerId + "leaved from lobby";
    }
    
    public static String getReadyPlayersStatusMessage(int playersAmount){
        return String.format("Number of ready players: %d", playersAmount);
    }
    
}
