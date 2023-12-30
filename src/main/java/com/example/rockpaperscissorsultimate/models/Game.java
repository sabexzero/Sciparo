package com.example.rockpaperscissorsultimate.models;


import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//TODO: Переделать отношения между сущностями, нынешняя версия абсолютно неправильная

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue
    private UUID id;
    
    @OneToOne
    private Player winner;
    
    @OneToOne
    private Player loser;
    
    private PlayerChoice winnerChoice;
    private PlayerChoice loserChoice;
    private Long bet;
}