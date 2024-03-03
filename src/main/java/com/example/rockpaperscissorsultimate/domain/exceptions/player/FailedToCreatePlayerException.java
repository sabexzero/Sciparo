package com.example.rockpaperscissorsultimate.domain.exceptions.player;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.domain.exceptions.general.FailedToCreateException;

public class FailedToCreatePlayerException extends FailedToCreateException {
    public FailedToCreatePlayerException() {
        super(Player.class.getName().toLowerCase());
    }
}
