package com.example.rockpaperscissorsultimate.domain.exceptions.game;

import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.domain.exceptions.general.FailedToCreateException;

public class FailedToCreateGameException extends FailedToCreateException {
    public FailedToCreateGameException(){
        super(Game.class.getName().toLowerCase());
    }
}
