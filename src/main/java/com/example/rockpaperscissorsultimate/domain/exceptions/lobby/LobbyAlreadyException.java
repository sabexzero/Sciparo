package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class LobbyAlreadyException extends CustomException {
    public LobbyAlreadyException() {
        super(CustomLobbyExceptionMessages.LOBBY_IS_READY);
    }
}
