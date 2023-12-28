package com.example.rockpaperscissorsultimate.utils.exceptions.Player;

//TODO: Реализовать ошибку
//TODO: Также необходимо реализовать другие ошибки, продумать какие
public class InvalidUpdatingBalanceException extends RuntimeException{
    public InvalidUpdatingBalanceException(String message) {
        super(message);
    }
}
