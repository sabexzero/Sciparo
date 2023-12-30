package com.example.rockpaperscissorsultimate;

import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.repositories.PlayerRepository;
import com.example.rockpaperscissorsultimate.services.BalanceService;
import com.example.rockpaperscissorsultimate.services.EloService;
import com.example.rockpaperscissorsultimate.services.PlayerService;
import com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants;
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

import static com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants.ELO_MINIMUM;

@ExtendWith(MockitoExtension.class)
public class EloServiceTest {
    
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PlayerService playerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private EloService eloService;
    
    @Test
    public void shouldIncreaseEloBy5_WhenPlayerIsWinner() {
        // Arrange
        Player player = new Player();
        player.setElo(10);
        boolean isWinner = true;
        
        // Act
        eloService.updatePlayerElo(player,isWinner);
        
        // Assert
        Assertions.assertEquals(15, player.getElo());
        
        // Verify that playerService.updatePlayer() was called with the correct player
        Mockito.verify(playerService, Mockito.times(1)).updatePlayer(Mockito.eq(player));
    }
    
    @Test
    public void shouldDecreaseEloBy5_WhenPlayerIsLoser() {
        // Arrange
        Player player = new Player();
        player.setElo(10);
        boolean isWinner = false;
        
        // Act
        eloService.updatePlayerElo(player, isWinner);
        
        // Assert
        Assertions.assertEquals(5, player.getElo());
        Mockito.verify(playerService, Mockito.times(1)).updatePlayer(player);
    }
    @Test
    public void shouldLeaveEloMinimum_WhenPlayerIsLoserAndAlreadyHaveMinimum() {
        // Arrange
        Player player = new Player();
        player.setElo(ELO_MINIMUM);
        boolean isWinner = false;
        
        // Act
        eloService.updatePlayerElo(player, isWinner);
        
        // Assert
        Assertions.assertEquals(ELO_MINIMUM, player.getElo());
        Mockito.verify(playerService, Mockito.times(1)).updatePlayer(player);
    }
    
}