package com.example.rockpaperscissorsultimate.controllers;

import com.example.rockpaperscissorsultimate.models.Lobby;
import com.example.rockpaperscissorsultimate.services.GameService;
import com.example.rockpaperscissorsultimate.utils.dtos.MoveResponse;
import com.example.rockpaperscissorsultimate.utils.dtos.RegisterMoveRequest;
import com.example.rockpaperscissorsultimate.utils.game.GameUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/game")
public class GameController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    
    public String index(){
        return "index";
    }
    
    @MessageMapping("/registerGame")
    public HttpStatus registerGame(Lobby lobby){
        try {
            gameService.createGame(lobby);
            
            String topic = "/topic/game/" + lobby.getId(); //We are creating a connection using the lobby id for the game
            
            messagingTemplate.convertAndSend(topic, GameUtils.GAME_START_MESSAGE);
            
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    @MessageMapping("/registerMove")
    public HttpStatus registerMove(RegisterMoveRequest request){
        try {
            String topic = "/topic/game/" + request.getLobbyId();
            
            MoveResponse moveResponse = gameService.registerMove(request);
            
            messagingTemplate.convertAndSend(topic, GameUtils.getRoundResultString(moveResponse.getGame().getRoundsAmount(),moveResponse.getMoveResult().toString()));
            
            if(moveResponse.isLastMove()){
                gameService.conductGame(moveResponse.getGame());
                messagingTemplate.convertAndSend(topic, GameUtils.getGameResultString(moveResponse.getMoveResult().toString()));
            }
            
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
}
