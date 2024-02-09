package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.utils.exceptions.Player.InvalidUpdatingBalanceException;
import com.example.rockpaperscissorsultimate.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service@AllArgsConstructor
public class BalanceService {
    private final PlayerService playerService;
    
    public void updatePlayerBalance(Player entity, int bet, boolean isWinner){
        var deltaBet = isWinner ? bet : -bet;
        entity.setCoins(getChangedBalance(entity.getCoins(),deltaBet));
        playerService.updatePlayer(entity);
    }
    
    private static Long getChangedBalance(Long initialAmount, int bet) {
        
        if (initialAmount + bet < 0) {
            throw new InvalidUpdatingBalanceException("Updating the balance is not possible");
        }
        return initialAmount + bet;
    }
}
