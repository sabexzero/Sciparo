package com.example.rockpaperscissorsultimate.utils.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateLobbyRequest {
    private String creatorId;
    private int bet;
}
