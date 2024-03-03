package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.lobby.Lobby;
import com.example.rockpaperscissorsultimate.repository.LobbyRepository;
import com.example.rockpaperscissorsultimate.web.dto.lobby.CreateLobbyDTO;
import com.example.rockpaperscissorsultimate.domain.exceptions.general.SomethingWrongException;
import com.example.rockpaperscissorsultimate.domain.exceptions.player.PlayerNotFoundException;
import com.example.rockpaperscissorsultimate.domain.exceptions.lobby.*;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LobbyService {
    private LobbyRepository lobbyRepository;
    
    public void createLobby(CreateLobbyDTO dto){
        
        if (dto == null || dto.getCreator() == null)
            throw new SomethingWrongException();
        
        if(dto.getCreator().getCoins() < dto.getBet())
            throw new NotEnoughCoinsException();
        
        var createdLobby = Lobby.builder()
                .bet(dto.getBet())
                .playersId(List.of(dto.getCreator().getId()))
                .title(dto.getTitle())
                .readyPlayersNumber(0)
                .creatorElo(dto.getCreator().getElo())
                .creatorUsername(dto.getCreator().getUsername())
                .build();
        
        lobbyRepository.save(createdLobby);
    }
    
    public List<Lobby> getAllLobbies(){
        return lobbyRepository.findAll();
    }
    
    public Lobby getLobbyById(String lobbyId){
        return lobbyRepository.findById(lobbyId).orElseThrow(() -> new LobbyNotFoundException(lobbyId));
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
            throw new PlayerNotFoundException(leavedPlayerId);
        
        playersId.remove(leavedPlayerId);
        lobby.setPlayersId(playersId);
        
        if(playersId.isEmpty())
            lobbyRepository.deleteById(lobbyId);
        else
            lobbyRepository.save(lobby);
    }
    
    public int addReadyStatus(String lobbyId){
        var lobby = lobbyRepository.findById(lobbyId).orElseThrow(() -> new LobbyNotFoundException(lobbyId));
        
        if(lobby.getReadyPlayersNumber() == GameUtils.MAX_PLAYERS)
            throw new LobbyAlreadyException();
        
        lobby.setReadyPlayersNumber(lobby.getReadyPlayersNumber() + 1);
        
        lobbyRepository.save(lobby);
        
        return lobby.getReadyPlayersNumber();
    }
    
    public int removeReadyStatus(String lobbyId){
        var lobby = lobbyRepository.findById(lobbyId).orElseThrow(() -> new LobbyNotFoundException(lobbyId));
        
        if(lobby.getReadyPlayersNumber() == 0)
            throw new NoOneReadyInLobbyException();
        
        lobby.setReadyPlayersNumber(lobby.getReadyPlayersNumber() - 1);
        
        lobbyRepository.save(lobby);
        
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
