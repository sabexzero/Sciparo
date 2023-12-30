package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_CHANGE;
import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_MINIMUM;

@Service
@AllArgsConstructor
public class EloService {
    private final PlayerService playerService;
    
    public void updatePlayerElo(Player entity, boolean isWinner){
        int deltaElo = isWinner ? ELO_CHANGE : ELO_CHANGE * -1; // Если проигрыш, изменение ELO будет отрицательным
        entity.setElo(getChangedElo(entity.getElo(), deltaElo));
        playerService.updatePlayer(entity);
    }
    
    private static int getChangedElo(int initialElo, int deltaElo){
        return Math.max(initialElo + deltaElo, ELO_MINIMUM); // Используем deltaElo для обновления ELO
    }
    
}
