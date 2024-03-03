package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class NotEnoughPlayersToStartException extends CustomException {
    public NotEnoughPlayersToStartException() {
        super(CustomLobbyExceptionMessages.NOT_ENOUGH_PLAYERS);
    }
}
