package com.example.rockpaperscissorsultimate.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Outcome {
    WIN(1),
    LOSE(-1);
    private final int value;
    
}
