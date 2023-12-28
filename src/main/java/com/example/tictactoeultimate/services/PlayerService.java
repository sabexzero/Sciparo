package com.example.tictactoeultimate.services;

import com.example.tictactoeultimate.dtos.CreatePlayerRequest;
import com.example.tictactoeultimate.models.Player;
import com.example.tictactoeultimate.repositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
//TODO: Продумать, какие методы еще могут пригодится при обработке пользователей
//TODO: Добавить валидацию данных
//TODO: Начать писать тесты, проверять покрытие
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    
    public Player createPlayer(CreatePlayerRequest request){
        String passwordHash = passwordEncoder.encode(request.getPassword());
        
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
        return playerRepository.save(newPlayer);
    }
    
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }
    
    public Player getPlayerById(UUID id){
        return playerRepository.findById(id).orElse(null);
    }
    
    public Player updatePlayer(Player entity){
        return playerRepository.save(entity);
    }
    
    public void deletePlayer(UUID id){
        playerRepository.deleteById(id);
    }
}