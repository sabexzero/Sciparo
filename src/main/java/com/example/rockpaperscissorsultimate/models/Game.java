package com.example.rockpaperscissorsultimate.models;


import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue
    private UUID id;
    
    @ManyToOne
    private Player winner;
    
    @ManyToOne
    private Player loser;
    
    @Enumerated(EnumType.STRING)
    private PlayerChoice winnerChoice;
    
    @Enumerated(EnumType.STRING)
    private PlayerChoice loserChoice;
    private Long bet;
}