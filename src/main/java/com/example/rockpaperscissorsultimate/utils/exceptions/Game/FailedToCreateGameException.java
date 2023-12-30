package com.example.rockpaperscissorsultimate.utils.exceptions.Game;

import org.apache.logging.log4j.message.Message;

public class FailedToCreateGameException extends RuntimeException{
    public FailedToCreateGameException(String message){
        super(message);
    }
}
