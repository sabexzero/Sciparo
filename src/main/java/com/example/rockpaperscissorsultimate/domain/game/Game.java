package com.example.rockpaperscissorsultimate.domain.game;


import com.example.rockpaperscissorsultimate.domain.player.Player;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    
    private int roundsAmount;
    private int readyPlayers;
    private GameResult gameResult;
    private GameStatus gameStatus;
    private Integer firstPlayerWinRounds;
    private Integer secondPlayerWinRounds;
    private Integer bet;

}