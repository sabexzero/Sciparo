package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.utils.constants.PlayerConstants;
import com.example.rockpaperscissorsultimate.utils.dtos.CreatePlayerRequest;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.FailedToCreatePlayerException;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.PlayerNotFoundException;
import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.repositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
//TODO: Продумать, какие методы еще могут пригодится при обработке пользователей
//TODO: Добавить валидацию данных
//TODO: Начать писать тесты, проверять покрытие
public class PlayerService implements UserDetailsService {
    
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    
    public Player createPlayer(@NotNull CreatePlayerRequest request){
        String passwordHash = passwordEncoder.encode(request.getPasswordText());
        
        Player newPlayer = Player.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .name(request.getName())
                .passwordHash(passwordHash)
                .coins(PlayerConstants.BALANCE_START)
                .elo(PlayerConstants.ELO_MINIMUM)
                .loses(0)
                .wins(0)
                .build();
        
        try{
            return playerRepository.save(newPlayer);
        }catch (Exception exception){
            throw new FailedToCreatePlayerException("An error occurred when registering a player");
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

    public void updatePlayer(Player entity){
        playerRepository.save(entity);
    }
    
    public void deletePlayerById(UUID id){
        playerRepository.deleteById(id);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = getPlayerByName(username);
        if(player == null)
            throw new UsernameNotFoundException("Username not found");
        
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(player.getRole().getTitle()));
        return new User(
                player.getUsername(),
                player.getPasswordHash(),
                authorities) {
        };
    }
}