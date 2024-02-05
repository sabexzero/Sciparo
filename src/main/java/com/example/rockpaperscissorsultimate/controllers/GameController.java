package com.example.rockpaperscissorsultimate.controllers;

import com.example.rockpaperscissorsultimate.models.Lobby;
import com.example.rockpaperscissorsultimate.services.GameService;
import com.example.rockpaperscissorsultimate.services.LobbyService;
import com.example.rockpaperscissorsultimate.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/rps")
public class GameController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final LobbyService lobbyService;
    private final PlayerService playerService;
    private final GameService gameService;
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
    
    //TODO: Change to constant, create messagesConstant for webSockets and paths
    @MessageMapping("/createNewLobby")
    public HttpStatus createLobby(){
        String topic = "/topic/lobby";
        try {
            lobbyService.createLobby();
            messagingTemplate.convertAndSend(topic,"cr");
            return HttpStatus.OK;
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @MessageMapping("/joinPlayerLobby")
    @SendTo("/topic/lobby/{lobbyId}")
    public HttpStatus joinPlayerInLobby(String lobbyId, String playerId){
        
        String topic = "/topic/lobby/" + lobbyId;
        
        try {
            gameService.joinPlayerLobby(lobbyId,playerId);
            //TODO: Change to constant, create messagesConstant for webSockets
            messagingTemplate.convertAndSend(topic,"player join lobby");
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @ResponseBody
    @GetMapping("/getAllLobbies")
    public List<Lobby> getAllLobbies(){
        return lobbyService.getAllLobbies();
    }
}
