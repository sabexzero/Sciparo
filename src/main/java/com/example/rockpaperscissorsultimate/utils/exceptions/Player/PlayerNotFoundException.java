package com.example.rockpaperscissorsultimate.utils.exceptions.Player;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message){
        super(message);
    }
}
