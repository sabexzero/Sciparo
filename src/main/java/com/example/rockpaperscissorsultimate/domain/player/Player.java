package com.example.rockpaperscissorsultimate.domain.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection  = "players")
public class Player implements Serializable {
    @Id
    private String id;
    
    private String username;
    private String email;
    private Integer wins;
    private Integer loses;
    private Integer draws;
    private Integer elo;
    private Set<Role> roles;
    private Integer gamesAmount;
    private PlayerStatus playerStatus;
    private Long coins;
    private String passwordHash;
    
}
