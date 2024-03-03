package com.example.rockpaperscissorsultimate.domain.exceptions.player;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.NotFoundException;

public class PlayerNotFoundByIdException extends NotFoundException {
    public PlayerNotFoundByIdException(String id) {
        super(String.format("id = %s",id));
    }
}
