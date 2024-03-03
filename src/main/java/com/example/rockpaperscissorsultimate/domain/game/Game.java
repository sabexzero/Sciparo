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
    
    private String lobbyId;
    private int roundsAmount;
    private GameResult gameResult;
    private Integer firstPlayerWinRounds;
    private Integer secondPlayerWinRounds;
    private Integer bet;

}