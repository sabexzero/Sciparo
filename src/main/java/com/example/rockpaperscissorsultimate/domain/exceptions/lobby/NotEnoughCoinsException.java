package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class NotEnoughCoinsException extends CustomException {
    public NotEnoughCoinsException() {
        super(CustomLobbyExceptionMessages.NOT_ENOUGH_COINS);
    }
}
