package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.utils.enums.Outcome;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.InvalidUpdatingBalanceException;
import com.example.rockpaperscissorsultimate.models.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {
    private final PlayerService playerService;
    
    //Outcome: Outcome.Win = 1, Outcome.Lose = -1;
    public Player updatePlayerBalance(Player entity, long bet, Outcome outcome){
        var deltaBet = bet * outcome.getValue();
        entity.setCoins(getChangedBalance(entity.getCoins(),deltaBet));
        return playerService.updatePlayer(entity);
    }
    
    private static Long getChangedBalance(Long initialAmount, Long bet){
        if(initialAmount + bet < 0 || initialAmount > Long.MAX_VALUE - bet)
            throw new InvalidUpdatingBalanceException("Updating the balance is not possible");
        return initialAmount + bet;
    }
}
