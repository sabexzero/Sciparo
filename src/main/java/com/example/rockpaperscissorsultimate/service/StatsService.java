package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatsService {
    
    private final BalanceService balanceService;
    private final EloService eloService;
    private void registerLose(Player player, int bet){
        player.setLoses(player.getLoses()+1);
        balanceService.updatePlayerBalance(player,bet,false);
        eloService.updatePlayerElo(player,false);
    }
    
    private void registerWin(Player player, int bet){
        player.setWins(player.getWins()+1);
        balanceService.updatePlayerBalance(player,bet,true);
        eloService.updatePlayerElo(player,true);
    }
    
    /**
     * If the game did not end in a draw
     */
    public void changeStatsAfterGame(Player winner, Player loser, int bet){
        registerWin(winner,bet);
        registerLose(loser,bet);
    }
}
