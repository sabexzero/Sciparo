package com.example.rockpaperscissorsultimate.controllers;


import com.example.rockpaperscissorsultimate.models.Lobby;
import com.example.rockpaperscissorsultimate.services.LobbyService;
import com.example.rockpaperscissorsultimate.utils.dtos.CreateLobbyRequest;
import com.example.rockpaperscissorsultimate.utils.dtos.LobbyPlayerActionRequest;
import com.example.rockpaperscissorsultimate.utils.lobby.LobbyUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/lobby")
public class LobbyController {
    private final LobbyService lobbyService;
    private final SimpMessagingTemplate messagingTemplate;
    @PostMapping("/create")
    public HttpStatus createLobby(@RequestBody CreateLobbyRequest request){
        String topic = "/topic/lobby";
        try {
            lobbyService.createLobby(request);
            messagingTemplate.convertAndSend(topic,"cr");
            return HttpStatus.OK;
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @ResponseBody
    @GetMapping("/getAll")
    public List<Lobby> getAllLobbies(){
        return lobbyService.getAllLobbies();
    }
    
    @PostMapping("/joinPlayer")
    public HttpStatus joinPlayerInLobby(@RequestBody LobbyPlayerActionRequest request){
        
        String topic = "/topic/lobby/" + request.getLobbyId();
        
        try {
            lobbyService.joinPlayerLobby(request.getLobbyId(),request.getPlayerId());
            messagingTemplate.convertAndSend(topic, LobbyUtils.playerJoinMessage(request.getPlayerId()));
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @PostMapping("/leavePlayer")
    public HttpStatus leavePlayerFromLobby(@RequestBody LobbyPlayerActionRequest request){
        
        String topic = "/topic/lobby/" + request.getLobbyId();
        try {
            lobbyService.leavePlayerLobby(request.getLobbyId(),request.getPlayerId());
            messagingTemplate.convertAndSend(topic,LobbyUtils.playerLeaveMessage(request.getPlayerId()));
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @PostMapping("/addReadyStatus")
    public HttpStatus addReadyStatus(String lobbyId){
        
        String topic = "/topic/lobby/" + lobbyId;
        try {
            int readyPlayersNumber = lobbyService.addReadyStatus(lobbyId);
            messagingTemplate.convertAndSend(topic, LobbyUtils.getReadyPlayersStatusMessage(readyPlayersNumber));
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @PostMapping("/removeReadyStatus")
    public HttpStatus removeReadyStatus(String lobbyId){
        String topic = "/topic/lobby/" + lobbyId;
        try {
            int readyPlayersNumber = lobbyService.removeReadyStatus(lobbyId);
            messagingTemplate.convertAndSend(topic, LobbyUtils.getReadyPlayersStatusMessage(readyPlayersNumber));
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @PostMapping("/removeLobbyToRegisterGame")
    public HttpStatus removeLobbyToRegisterGame(String lobbyId){
        record LobbyMessage(Lobby lobby, String message) {}
        
        String topic = "/topic/game/" + lobbyId;
        try {
            var lobby = lobbyService.getLobbyById(lobbyId);
            
            lobbyService.removeLobbyToRegisterGame(lobby);
            messagingTemplate.convertAndSend(topic,new LobbyMessage(lobby,LobbyUtils.ALL_PLAYERS_READY_MESSAGE));
            
            return HttpStatus.OK;
            
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
}
