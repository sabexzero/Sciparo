package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.lobby.Lobby;
import com.example.rockpaperscissorsultimate.domain.exceptions.general.FailedToCreateException;

public class FailedToCreateLobbyException extends FailedToCreateException {
    public FailedToCreateLobbyException() {
        super(Lobby.class.getName().toLowerCase());
    }
}
