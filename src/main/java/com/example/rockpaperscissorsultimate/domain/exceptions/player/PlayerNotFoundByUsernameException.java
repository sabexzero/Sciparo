package com.example.rockpaperscissorsultimate.domain.exceptions.player;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.NotFoundException;

public class PlayerNotFoundByUsernameException extends NotFoundException {
    public PlayerNotFoundByUsernameException(String username) {
        super(String.format("username = %s",username));
    }
}
