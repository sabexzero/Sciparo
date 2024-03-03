package com.example.rockpaperscissorsultimate.web.controllers;

import com.example.rockpaperscissorsultimate.domain.lobby.Lobby;
import com.example.rockpaperscissorsultimate.service.GameService;
import com.example.rockpaperscissorsultimate.web.dto.game.MoveResponse;
import com.example.rockpaperscissorsultimate.web.dto.game.RegisterMoveRequest;
import com.example.rockpaperscissorsultimate.domain.exceptions.general.FailedToCreateException;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for creating games and
 * performing all the actions inside each game
 */

@RestController
@AllArgsConstructor
@RequestMapping("/games")
@Tag(
        name="GameController",
        description="This controller is responsible for creating games and" +
        " performing all the actions inside each game")
public class GameController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    @MessageMapping("/games/new")
    @PostMapping("/new")
    @Operation(
            summary = "Creating game",
            description = "Allows you to create a game based on the existing lobby, " +
                    "if all the players are gathered and ready to start the game"
    )
    @ResponseBody
    public void registerGame(@RequestBody @Parameter(description = "The essence of the lobby on the basis of which the game is created") Lobby lobby){
        String mainTopic = "/topic/game";
        try {
            gameService.createGame(lobby);
            String gameTopic = mainTopic + lobby.getId();
            
            messagingTemplate.convertAndSend(gameTopic, GameUtils.GAME_START_MESSAGE);
            messagingTemplate.convertAndSend(mainTopic, GameUtils.GAME_START_MESSAGE);
        }catch (Exception ex){
            messagingTemplate.convertAndSend(mainTopic, new FailedToCreateException(lobby.getId()).getMessage());
        }
    }
    @MessageMapping("games/move")
    @PostMapping("/move")
    @Operation(
            summary = "Registers the player's move",
            description = "Registers the player's move during the game"
    )
    @ResponseBody
    public void registerMove(@RequestBody @Parameter(description = "The essence of the request for registration of the move") RegisterMoveRequest request){
        String mainTopic = "/topic/game";
        
        try {
            String topic = "/topic/game/" + request.getLobbyId();
            MoveResponse moveResponse = gameService.registerMove(request);
            
            messagingTemplate.convertAndSend(topic, GameUtils.getRoundResultString(moveResponse.getGame().getRoundsAmount(),moveResponse.getMoveResult().toString()));
            
            if(moveResponse.isLastMove()){
                gameService.conductGame(moveResponse.getGame());
                messagingTemplate.convertAndSend(topic, GameUtils.getGameResultString(moveResponse.getMoveResult().toString()));
            }
            messagingTemplate.convertAndSend(mainTopic, GameUtils.GAME_START_MESSAGE);
            
        }catch (Exception ex){
            messagingTemplate.convertAndSend(mainTopic, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }
    
}
