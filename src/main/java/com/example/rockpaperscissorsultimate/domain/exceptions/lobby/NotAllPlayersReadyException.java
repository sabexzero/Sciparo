package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class NotAllPlayersReadyException extends CustomException {
    public NotAllPlayersReadyException() {
        super(CustomLobbyExceptionMessages.NOT_ALL_PLAYERS_READY);
    }
}
