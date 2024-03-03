package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class NoOneReadyInLobbyException extends CustomException {
    public NoOneReadyInLobbyException() {
        super(CustomLobbyExceptionMessages.NO_ONE_READY);
    }
}
