package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatsService {
    
    private final BalanceService balanceService;
    private final EloService eloService;
    public void registerLose(Player player, Long bet){
        player.setLoses(player.getLoses()+1);
        balanceService.updatePlayerBalance(player,bet,false);
        eloService.updatePlayerElo(player,false);
    }
    
    public void registerWin(Player player, Long bet){
        player.setWins(player.getWins()+1);
        balanceService.updatePlayerBalance(player,bet,true);
        eloService.updatePlayerElo(player,true);
    }
}
