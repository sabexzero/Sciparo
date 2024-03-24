package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.rockpaperscissorsultimate.utils.PlayerUtils.ELO_CHANGE;
import static com.example.rockpaperscissorsultimate.utils.PlayerUtils.ELO_MINIMUM;


@Service
@AllArgsConstructor
public class EloService {
    
    /**
     * The method is used only if the game did not end in a draw
     * Determine what needs to be done with the elo based on the result of the game, add or subtract
     */
    public void updatePlayerElo(
            Player player,
            boolean isWinner
    ){
        int deltaElo = isWinner ? ELO_CHANGE : ELO_CHANGE * -1;
        player.setElo(getChangedElo(player.getElo(), deltaElo));
    }
    
    /**
     * The method is used only if the game ended in a draw
     */
    public void updatePlayerElo(
            Player player
    ){
        player.setElo(getChangedElo(player.getElo(), GameUtils.DRAW_DELTA_ELO));
    }
    
    private int getChangedElo(
            int initialElo,
            int deltaElo
    ){
        return Math.max(initialElo + deltaElo, ELO_MINIMUM);
    }
    
}
