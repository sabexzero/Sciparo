package com.example.rockpaperscissorsultimate.models;


import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@Document(collection = "games")
public class Game {
    @Id
    private String id;
    
    @DBRef
    private Player firstPlayer;
    
    @DBRef
    private Player secondPlayer;
    
    private String lobbyId;
    
    private int roundsAmount;
    
    private GameResult gameResult;
    
    private int firstPlayerWinRounds;
    
    private int secondPlayerWinRounds;
    
    private int bet;

}