package com.example.rockpaperscissorsultimate.domain.exceptions.lobby;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.NotFoundException;

public class LobbyNotFoundException extends NotFoundException {
    public LobbyNotFoundException(String id) {
        super(String.format("id = %s",id));
    }
}
