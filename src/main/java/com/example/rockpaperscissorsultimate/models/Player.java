package com.example.rockpaperscissorsultimate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    
    private int draws;
    
    private int elo;
    
    private Long coins;
    
    private String passwordHash;
    
    public void ChangeBalance(long bet){
        coins+=bet;
    }
    public void IncreaseElo(){
        elo+= ELO_CHANGE;
    }
    
    public void DecreaseElo(){

    }
}
