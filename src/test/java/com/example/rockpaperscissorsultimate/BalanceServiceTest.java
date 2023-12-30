package com.example.rockpaperscissorsultimate;

import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.repositories.PlayerRepository;
import com.example.rockpaperscissorsultimate.services.BalanceService;
import com.example.rockpaperscissorsultimate.services.PlayerService;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.InvalidUpdatingBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {
    
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PlayerService playerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private BalanceService balanceService;
    
    @Test
    public void shouldIncreaseBalanceBy50_WhenPlayerIsWinner() {
        // Arrange
        Player player = new Player();
        player.setCoins(100L);
        long bet = 50L;
        boolean isWinner = true;
        
        // Act
        balanceService.updatePlayerBalance(player, bet, isWinner);
        
        // Assert
        Assertions.assertEquals(150L, player.getCoins());
        Mockito.verify(playerService, Mockito.times(1)).updatePlayer(Mockito.eq(player));
    }
    
    @Test
    public void shouldDecreaseBalanceBy50_WhenPlayerIsWinner() {
        // Arrange
        Player player = new Player();
        player.setCoins(100L);
        long bet = 50L;
        boolean isWinner = false;
        
        // Act
        balanceService.updatePlayerBalance(player, bet, isWinner);
        
        // Assert
        Assertions.assertEquals(50L, player.getCoins());
        Mockito.verify(playerService, Mockito.times(1)).updatePlayer(player);
    }
    
    @Test
    public void testUpdatePlayerBalance_InvalidBalance() {
        // Arrange
        Player player = new Player();
        player.setCoins(100L);
        long bet = 200L;
        boolean isWinner = false;
        
        // Act & Assert
        Assertions.assertThrows(InvalidUpdatingBalanceException.class, () ->
                balanceService.updatePlayerBalance(player, bet, isWinner));
        Mockito.verify(playerService, Mockito.never()).updatePlayer(player);
    }
}
