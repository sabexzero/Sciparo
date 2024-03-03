package com.example.rockpaperscissorsultimate.domain.exceptions.player;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.NotFoundException;

public class PlayerNotFoundByEmailException extends NotFoundException {
    public PlayerNotFoundByEmailException(String email) {
        super(String.format("email = %s",email));
    }
}
