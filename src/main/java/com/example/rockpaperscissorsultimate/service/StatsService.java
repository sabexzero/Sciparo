package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatsService {
    
    private final BalanceService balanceService;
    private final EloService eloService;
    public void registerLose(Player player, int bet){
        player.setLoses(player.getLoses()+1);
        balanceService.updatePlayerBalance(player,bet,false);
        eloService.updatePlayerElo(player,false);
    }
    
    public void registerWin(Player player, int bet){
        player.setWins(player.getWins()+1);
        balanceService.updatePlayerBalance(player,bet,true);
        eloService.updatePlayerElo(player,true);
    }
    
    public void registerDraw(Player player, int bet){
        player.setDraws(player.getDraws()+1);
        balanceService.updatePlayerBalance(player,bet);
        eloService.updatePlayerElo(player);
    }
    
}
