package com.example.rockpaperscissorsultimate.domain.exceptions.general;


public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
