package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.utils.dtos.CreatePlayerRequest;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.CreatePlayerFailedException;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.PlayerNotFoundException;
import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.repositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
//TODO: Продумать, какие методы еще могут пригодится при обработке пользователей
//TODO: Добавить валидацию данных
//TODO: Начать писать тесты, проверять покрытие
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    
    public Player createPlayer(@NotNull CreatePlayerRequest request){
        String passwordHash = passwordEncoder.encode(request.getPasswordText());
        
        Player newPlayer = Player.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordHash)
                .coins(0L)
                .elo(0)
                .draws(0)
                .loses(0)
                .wins(0)
                .build();
        
        try{
            return playerRepository.save(newPlayer);
        }catch (Exception exception){
            throw new CreatePlayerFailedException("An error occurred when registering a player");
        }

    }
    
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }
    
    public Player getPlayerById(UUID id){
        var player = playerRepository.findById(id).orElse(null);
        if(player == null)
            throw new PlayerNotFoundException("The player was not found");
        return player;
    }
    
    public Player getPlayerByName(String findName){
    var player = playerRepository.findByUsername(findName);
        if(player == null)
            throw new PlayerNotFoundException("The player was not found");
        return player;
    }

    public Player updatePlayer(Player entity){
        return playerRepository.save(entity);
    }
    
    public void deletePlayer(UUID id){
        playerRepository.deleteById(id);
    }
}