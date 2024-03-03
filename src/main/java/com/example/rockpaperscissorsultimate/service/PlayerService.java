package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.utils.PlayerUtils;
import com.example.rockpaperscissorsultimate.web.dto.player.SignUpPlayerRequest;
import com.example.rockpaperscissorsultimate.domain.player.PlayerStatus;
import com.example.rockpaperscissorsultimate.domain.player.Role;
import com.example.rockpaperscissorsultimate.domain.exceptions.player.FailedToCreatePlayerException;
import com.example.rockpaperscissorsultimate.domain.exceptions.player.PlayerNotFoundException;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    
    public Player createPlayer(@NotNull SignUpPlayerRequest request){
        Player newPlayer = Player.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPasswordText())
                .coins(PlayerUtils.BALANCE_START)
                .elo(PlayerUtils.ELO_MINIMUM)
                .loses(0)
                .wins(0)
                .draws(0)
                .playerStatus(PlayerStatus.READY)
                .gamesAmount(0)
                .roles(Set.of(Role.valueOf("USER")))
                .build();
        
        try{
            return playerRepository.save(newPlayer);
        }catch (Exception exception){
            throw new FailedToCreatePlayerException();
        }

    }
    
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }
    
    public Player getPlayerById(String id){
        var player = playerRepository.findById(id).orElse(null);
        if(player == null)
            throw new PlayerNotFoundException(id);
        return player;
    }
    
    public Player getPlayerByName(String findName){
    var player = playerRepository.findByUsername(findName);
        if(player == null)
            throw new PlayerNotFoundException(findName);
        return player;
    }
    
    public void changePlayerStatus(String playerId, PlayerStatus status){
        var player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        player.setPlayerStatus(status);
        playerRepository.save(player);
    }

    public void updatePlayer(Player entity){
        playerRepository.save(entity);
    }
    
    public void deletePlayerById(String id){
        playerRepository.deleteById(id);
    }
    
}