package com.example.rockpaperscissorsultimate.domain.exceptions.game;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.NotFoundException;

public class GameNotFoundByIdException extends NotFoundException {
    public GameNotFoundByIdException(String id){
        super("id = " + id);
    }
}