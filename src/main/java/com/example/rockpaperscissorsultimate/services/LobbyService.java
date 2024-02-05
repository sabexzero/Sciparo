package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Lobby;
import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.repositories.LobbyRepository;
import com.example.rockpaperscissorsultimate.utils.constants.exceptionMessages.LobbyExceptionMessages;
import com.example.rockpaperscissorsultimate.utils.enums.LobbyStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LobbyService {
    
    private final LobbyRepository lobbyRepository;
    
    public List<Lobby> getAllLobbies(){
        return lobbyRepository.findAll();
    }
    
    public void createLobby(){
        var newLobby = Lobby.builder()
                .games(new ArrayList<>())
                .playerOneWins(0)
                .playerTwoWins(0)
                .players(new ArrayList<>())
                .status(LobbyStatus.WAITING_PLAYERS)
                .gameResult(null)
                .build();
        lobbyRepository.save(newLobby);
    }
    public void joinPlayerLobby(String lobbyId, Player player){
         var lobby = lobbyRepository.findById(lobbyId).orElseThrow(() -> new RuntimeException(LobbyExceptionMessages.LOBBY_NOT_FOUND_BY_ID));
         lobby.getPlayers().add(player);
         lobbyRepository.save(lobby);
    }
}
