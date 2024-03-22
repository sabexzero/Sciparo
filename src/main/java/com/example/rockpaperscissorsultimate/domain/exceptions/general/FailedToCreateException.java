package com.example.rockpaperscissorsultimate.domain.exceptions.general;

public class FailedToCreateException extends RuntimeException{
    public FailedToCreateException(
            String message
    ){
        super(String.format("Failed to create a %s object", message));
    }
}
