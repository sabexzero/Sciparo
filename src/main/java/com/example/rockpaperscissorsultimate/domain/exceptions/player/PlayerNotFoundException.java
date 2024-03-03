package com.example.rockpaperscissorsultimate.domain.exceptions.player;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.NotFoundException;

public class PlayerNotFoundException extends NotFoundException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
