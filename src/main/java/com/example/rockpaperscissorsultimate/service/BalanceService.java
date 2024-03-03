package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.exceptions.player.InvalidUpdatingBalanceException;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {
    private final PlayerService playerService;
    
    /**
     * Determine what needs to be done with the bet based on the result of the game, add or subtract
     */
    public void updatePlayerBalance(Player player, int bet, boolean isWinner){
        var deltaBet = isWinner ? bet : -bet;
        player.setCoins(getChangedBalance(player.getCoins(),deltaBet));
        playerService.updatePlayer(player);
    }
    
    private static Long getChangedBalance(Long initialAmount, int bet) {
        
        if (initialAmount + bet < 0) {
            throw new InvalidUpdatingBalanceException("Updating the balance is not possible");
        }
        return initialAmount + bet;
    }
}
