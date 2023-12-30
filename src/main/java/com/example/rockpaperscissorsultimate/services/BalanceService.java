package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.utils.exceptions.Player.InvalidUpdatingBalanceException;
import com.example.rockpaperscissorsultimate.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {
    private final PlayerService playerService;
    
    public void updatePlayerBalance(Player entity, long bet, boolean isWinner){
        var deltaBet = isWinner ? bet : -bet;
        entity.setCoins(getChangedBalance(entity.getCoins(),deltaBet));
        playerService.updatePlayer(entity);
    }
    
    private static Long getChangedBalance(Long initialAmount, Long bet) {
        if ((bet > 0 && Long.MAX_VALUE - bet < initialAmount) ||
                (bet < 0 && initialAmount < Long.MIN_VALUE + bet)) {
            throw new InvalidUpdatingBalanceException("Updating the balance is not possible");
        }
        return initialAmount + bet;
    }
}
