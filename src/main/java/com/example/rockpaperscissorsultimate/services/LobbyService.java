package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Lobby;
import com.example.rockpaperscissorsultimate.repositories.LobbyRepository;
import com.example.rockpaperscissorsultimate.utils.dtos.CreateLobbyRequest;
import com.example.rockpaperscissorsultimate.utils.exceptions.Player.PlayerNotFoundException;
import com.example.rockpaperscissorsultimate.utils.exceptions.lobby.*;
import com.example.rockpaperscissorsultimate.utils.game.GameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LobbyService {
    
    @Autowired
    private LobbyRepository lobbyRepository;
    
    public void createLobby(CreateLobbyRequest request){
        var createdLobby = Lobby.builder()
                .bet(request.getBet())
                .playersId(List.of(request.getCreatorId()))
                .build();
        lobbyRepository.save(createdLobby);
    }
    
    public List<Lobby> getAllLobbies(){
        return lobbyRepository.findAll();
    }
    
    public Lobby getLobbyById(String lobbyId){
        return lobbyRepository.findById(lobbyId).orElseThrow(LobbyNotFoundException::new);
    }
    
    public void joinPlayerLobby(String lobbyId, String joinedPlayerId){
        var lobby = getLobbyById(lobbyId);
        if(lobby.getPlayersId().size() != GameUtils.MAX_PLAYERS){
            lobby.getPlayersId().add(joinedPlayerId);
            lobbyRepository.save(lobby);
        }
        else
            throw new LobbyFullException();
    }
    
    public void leavePlayerLobby(String lobbyId, String leavedPlayerId) {
        var lobby = getLobbyById(lobbyId);
        
        List<String> playersId = lobby.getPlayersId();
        
        if (!playersId.contains(leavedPlayerId))
            throw new PlayerNotFoundException();
        
        playersId.remove(leavedPlayerId);
        lobby.setPlayersId(playersId);
        
        if(playersId.isEmpty())
            lobbyRepository.deleteById(lobbyId);
        else
            lobbyRepository.save(lobby);
    }
    
    public int addReadyStatus(String lobbyId){
        var lobby = getLobbyById(lobbyId);
        
        if(lobby.getReadyPlayersNumber() == GameUtils.MAX_PLAYERS)
            throw new LobbyAlreadyException();
        
        lobby.setReadyPlayersNumber(lobby.getReadyPlayersNumber() + 1);
        
        return lobby.getReadyPlayersNumber();
    }
    
    public int removeReadyStatus(String lobbyId){
        var lobby = getLobbyById(lobbyId);
        
        if(lobby.getReadyPlayersNumber() == 0)
            throw new NoOneReadyInLobbyException();
        
        lobby.setReadyPlayersNumber(lobby.getReadyPlayersNumber() - 1);
        
        return lobby.getReadyPlayersNumber();
    }
    
    public void removeLobbyToRegisterGame(Lobby lobby){
        
        if(lobby.getPlayersId().size() != GameUtils.MAX_PLAYERS)
            throw new NotEnoughPlayersToStartException();
        
        if(lobby.getReadyPlayersNumber() != GameUtils.MAX_PLAYERS)
            throw new NotAllPlayersReadyException();
        
        lobbyRepository.delete(lobby);
    }
    
}
