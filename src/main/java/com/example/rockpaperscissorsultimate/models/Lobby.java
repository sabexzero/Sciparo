package com.example.rockpaperscissorsultimate.models;

import lombok.*;
import org.springframework.data.annotation.Id;
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
    
    private List<String> playersId;
    
    private int bet;
    
    private int readyPlayersNumber;
}
