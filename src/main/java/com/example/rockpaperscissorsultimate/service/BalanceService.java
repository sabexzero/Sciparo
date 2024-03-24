package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.exceptions.player.InvalidUpdatingBalanceException;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BalanceService {
    /**
     * The method is used only if the game did not end in a draw
     * Determine what needs to be done with the bet based on the result of the game, add or subtract
     */
    public void updatePlayerBalance(Player player, int bet, boolean isWinner){
        var deltaBet = isWinner ? bet : -bet;
        player.setCoins(getChangedBalance(player.getCoins(),deltaBet));
    }
    
    /**
     * The method is used only if the game ended in a draw
     */
    public void updatePlayerBalance(Player player, int bet){
        // Half of the bet is taken away from the player
        player.setCoins(getChangedBalance(player.getCoins(), (int)(-bet/2)));
    }
    
    
    private Long getChangedBalance(Long initialAmount, int bet) {
        
        if (initialAmount + bet < 0) {
            throw new InvalidUpdatingBalanceException("Updating the balance is not possible");
        }
        return initialAmount + bet;
    }
}
