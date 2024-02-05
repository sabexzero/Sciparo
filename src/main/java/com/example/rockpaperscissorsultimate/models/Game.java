package com.example.rockpaperscissorsultimate.models;


import com.example.rockpaperscissorsultimate.utils.enums.LobbyStatus;
import com.example.rockpaperscissorsultimate.utils.enums.PlayerChoice;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class Game {
    @Id
    private String id;
    
    @DBRef
    private Lobby lobby;
    
    private PlayerChoice player1Choice;
    
    private PlayerChoice player2Choice;
}