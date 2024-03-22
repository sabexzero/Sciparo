package com.example.rockpaperscissorsultimate.domain.exceptions.player;

import com.example.rockpaperscissorsultimate.domain.exceptions.general.CustomException;

public class InvalidUpdatingBalanceException extends CustomException {
    public InvalidUpdatingBalanceException(
            String message
    ) {
        super(message);
    }
}
