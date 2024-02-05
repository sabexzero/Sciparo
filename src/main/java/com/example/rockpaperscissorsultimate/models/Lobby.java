package com.example.rockpaperscissorsultimate.models;

import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import com.example.rockpaperscissorsultimate.utils.enums.LobbyStatus;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "lobbies")
public class Lobby {
    @Id
    private String id;
    
    @DBRef
    @Size(max = 2)
    private List<Player> players;
    
    private LobbyStatus status;
    
    private int playerOneWins;
    private int playerTwoWins;
    
    private GameResult gameResult;
    
    @DBRef
    private List<Game> games;
}
