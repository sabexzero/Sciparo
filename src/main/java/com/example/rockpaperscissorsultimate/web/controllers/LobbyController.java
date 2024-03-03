package com.example.rockpaperscissorsultimate.web.controllers;


import com.example.rockpaperscissorsultimate.domain.lobby.Lobby;
import com.example.rockpaperscissorsultimate.service.LobbyService;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.web.dto.lobby.CreateLobbyDTO;
import com.example.rockpaperscissorsultimate.web.dto.lobby.LobbyPlayerActionRequest;
import com.example.rockpaperscissorsultimate.utils.LobbyUtils;
import com.example.rockpaperscissorsultimate.web.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This controller is responsible for creating a lobby and
 * performing all actions inside each lobby
 */

@RestController
@AllArgsConstructor
@Tag(
        name="LobbyController",
        description="This controller is responsible for creating lobbies and" +
                " performing all the actions inside each lobby")
@RequestMapping("/lobbies")
public class LobbyController {
    private final LobbyService lobbyService;
    private final PlayerService playerService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SimpMessagingTemplate messagingTemplate;
    
    public record CreateLobbyRequest(String creatorId, String title, int bet){}
    @PostMapping("/new")
    public ResponseEntity<String> createLobby(@RequestBody CreateLobbyRequest request){
        String topic = "/topic/lobby";
        try {
            var findedPlayer = playerService.getPlayerById(request.creatorId);
            
            CreateLobbyDTO dto = CreateLobbyDTO.builder()
                    .creator(findedPlayer)
                    .title(request.title)
                    .bet(request.bet)
                    .build();
            
            lobbyService.createLobby(dto);
            getAllLobbiesWs();
            return ResponseEntity.ok("Success operation");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
    
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllLobbies() {
        try {
            Object obj = null;
            List<Lobby> lobbies = lobbyService.getAllLobbies();
            Map<String, Object> response = new HashMap<>();
            response.put("lobbies", lobbies);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            String errorMessage = "Failed to retrieve lobby list: " + ex.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorMessage", errorMessage);
            errorResponse.put("lobbies", Collections.emptyList());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
    }
    
    @MessageMapping("/lobbies/list")
    public void getAllLobbiesWs(){
        String topic = "/topic/lobby";
        var response = getAllLobbies();
        messagingTemplate.convertAndSend(topic,response);
    }
    
    @GetMapping("/getToken")
    public String getToken(){
        return jwtTokenProvider.createAccessTokenForTesting();
    }
    
    @GetMapping("/getValid")
    public boolean getToken(@RequestParam String token){
        return jwtTokenProvider.isValid(token);
    }
    
    @PostMapping("/join")
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
    
    @DeleteMapping("/leave")
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
    
    @PostMapping("/ready")
    public ResponseEntity<String> addReadyStatus(@RequestParam(name="id", required = true) String lobbyId){
        
        String topic = "/topic/lobby/" + lobbyId;
        try {
            int readyPlayersNumber = lobbyService.addReadyStatus(lobbyId);
            messagingTemplate.convertAndSend(topic, LobbyUtils.getReadyPlayersStatusMessage(readyPlayersNumber));
            
            if(readyPlayersNumber ==  2)
                removeLobbyToRegisterGame(lobbyId);
            
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
    @DeleteMapping("/ready")
    public ResponseEntity<String> removeReadyStatus(@RequestParam(name="id", required = true) String lobbyId){
        String topic = "/topic/lobby/" + lobbyId;
        try {
            int readyPlayersNumber = lobbyService.removeReadyStatus(lobbyId);
            messagingTemplate.convertAndSend(topic, LobbyUtils.getReadyPlayersStatusMessage(readyPlayersNumber));
            
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
    private void removeLobbyToRegisterGame(String lobbyId){
        
        String gameControllerPath = "/app/games/new";
        String topic = "/topic/lobby/" + lobbyId;
        var lobby = lobbyService.getLobbyById(lobbyId);
        
        messagingTemplate.convertAndSend(topic,LobbyUtils.ALL_PLAYERS_READY_MESSAGE);
        lobbyService.removeLobbyToRegisterGame(lobby);
        messagingTemplate.convertAndSend(gameControllerPath, lobby);
    }
}
