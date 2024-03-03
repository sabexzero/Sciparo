package com.example.rockpaperscissorsultimate.domain.exceptions.general;

public class SomethingWrongException extends CustomException{
    public SomethingWrongException() {
        super("Something wrong");
    }
}
