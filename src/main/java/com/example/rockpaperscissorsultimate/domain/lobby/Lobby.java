package com.example.rockpaperscissorsultimate.domain.lobby;

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
    
    private String title;
    private List<String> playersId;
    private String creatorUsername;
    private Integer creatorElo;
    private Integer bet;
    private Integer readyPlayersNumber;
}
