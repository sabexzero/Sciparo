package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.rockpaperscissorsultimate.utils.PlayerUtils.ELO_CHANGE;
import static com.example.rockpaperscissorsultimate.utils.PlayerUtils.ELO_MINIMUM;


@Service
@AllArgsConstructor
public class EloService {
    private final PlayerService playerService;
    
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updatePlayerElo(Player entity, boolean isWinner){
        int deltaElo = isWinner ? ELO_CHANGE : ELO_CHANGE * -1;
        entity.setElo(getChangedElo(entity.getElo(), deltaElo));
        playerService.updatePlayer(entity);
    }
    
    private static int getChangedElo(int initialElo, int deltaElo){
        return Math.max(initialElo + deltaElo, ELO_MINIMUM);
    }
    
}
