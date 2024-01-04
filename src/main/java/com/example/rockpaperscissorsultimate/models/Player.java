package com.example.rockpaperscissorsultimate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_CHANGE;
import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_MINIMUM;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue
    private UUID id;
    
    private String username;
    private String name;
    private String email;
    private int wins;
    private int loses;
    private int elo;
    private Long coins;
    private String passwordHash;
    
}
