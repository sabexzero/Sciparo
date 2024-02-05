package com.example.rockpaperscissorsultimate.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_CHANGE;
import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_MINIMUM;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection  = "players")
public class Player {
    @Id
    private String id;
    
    private String username;
    private String email;
    private int wins;
    private int loses;
    private int elo;
    private Long coins;
    private String passwordHash;
    
}
