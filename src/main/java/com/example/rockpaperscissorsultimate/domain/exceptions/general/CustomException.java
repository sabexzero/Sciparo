package com.example.rockpaperscissorsultimate.domain.exceptions.general;

//TODO: Remake exception handler
public class CustomException extends RuntimeException{
    public CustomException(
            String message
    ){
        super(message);
    }
}
