package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class LobbyFullException extends CustomException {
    public LobbyFullException() {
        super(CustomLobbyExceptionMessages.LOBBY_FULL);
    }
}
