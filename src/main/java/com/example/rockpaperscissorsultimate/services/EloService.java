package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.utils.enums.Outcome;
import com.example.rockpaperscissorsultimate.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_CHANGE;
import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_MINIMUM;

@Service
@AllArgsConstructor
public class EloService {
    private final PlayerService playerService;
    
    public Player updatePlayerElo(Player entity, Outcome outcome){
        int deltaElo = outcome.equals(Outcome.WIN) ? ELO_CHANGE : -ELO_CHANGE;
        entity.setElo(getChangedElo(entity.getElo(), deltaElo));
        return playerService.updatePlayer(entity);
    }
    
    private static int getChangedElo(int initialElo, int deltaElo){
        return Math.max(initialElo + ELO_CHANGE, ELO_MINIMUM);
    }
    
    
}
