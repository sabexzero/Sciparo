package com.example.rockpaperscissorsultimate.web.controllers;

import com.example.rockpaperscissorsultimate.domain.exceptions.game.FailedToCreateGameException;
import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.service.GameService;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import com.example.rockpaperscissorsultimate.web.dto.game.CreateGameRequest;
import com.example.rockpaperscissorsultimate.web.dto.game.MoveResponse;
import com.example.rockpaperscissorsultimate.web.dto.game.PlayerInOutRequest;
import com.example.rockpaperscissorsultimate.web.dto.game.RegisterMoveRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
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
        name="GameControllerWebSockets",
        description="This controller is responsible for creating games and" +
                " performing all the actions inside each game by websockets")
public class GameControllerWebSockets {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final PlayerService playerService;
    @MessageMapping("/games/new")
    @Operation(
            summary = "Creating game",
            description = "Allows you to create a game based on the existing lobby, " +
                    "if all the players are gathered and ready to start the game"
    )
    public void createGame(
            @RequestBody
            @Parameter(description = "The essence of the lobby on the basis of which the game is created")
            CreateGameRequest request
    ){
        String mainTopic = "/topic/game";
        try {
            Player creator = playerService.getPlayerById(request.creatorId());
            Game createdGame = gameService.createGame(creator,request.bet());
            String gameTopic = mainTopic + createdGame.getId();
            
            messagingTemplate.convertAndSend(gameTopic, GameUtils.GAME_START_MESSAGE);
            messagingTemplate.convertAndSend(mainTopic, GameUtils.GAME_START_MESSAGE);
        }catch (Exception ex){
            messagingTemplate.convertAndSend(mainTopic, new FailedToCreateGameException().getMessage());
        }
    }
    
    @MessageMapping("games/ready")
    public void readyPlayer(
            @RequestBody
            String gameId
    ){
        try {
            gameService.addReadyStatus(gameId);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @MessageMapping("games/unready")
    public void unreadyPlayer(
            @RequestBody
            String gameId
    ){
        try {
            gameService.removeReadyStatus(gameId);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @MessageMapping("games/join")
    @PostMapping("/join")
    public void joinPlayer(
            @RequestBody
            PlayerInOutRequest request
    ){
        try {
            Player player = playerService.getPlayerById(request.playerId());
            gameService.joinPlayer(request.gameId(),player);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @MessageMapping("games/leave")
    @PostMapping("/leave")
    public void leavePlayer(
            @RequestBody
            PlayerInOutRequest request
    ){
        try {
            Player player = playerService.getPlayerById(request.playerId());
            gameService.removePlayer(request.gameId(),player);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    
    @MessageMapping("games/move")
    @PostMapping("/move")
    @Operation(
            summary = "Registers the player's move",
            description = "Registers the player's move during the game"
    )
    public void registerMove(
            @RequestBody
            @Parameter(description = "The essence of the request for registration of the move")
            RegisterMoveRequest request
    ){
        String mainTopic = "/topic/game";
        
        try {
            String topic = "/topic/game/" + request.gameId();
            MoveResponse moveResponse = gameService.registerMove(request);
            
            messagingTemplate.convertAndSend(topic, GameUtils.getRoundResultString(moveResponse.game().getRoundsAmount(),moveResponse.moveResult().toString()));
            
            if(moveResponse.isLastMove()){
                gameService.conductGame(moveResponse.game());
                messagingTemplate.convertAndSend(topic, GameUtils.getGameResultString(moveResponse.moveResult().toString()));
            }
            messagingTemplate.convertAndSend(mainTopic, GameUtils.GAME_START_MESSAGE);
            
        }catch (Exception ex){
            messagingTemplate.convertAndSend(mainTopic, ex.getMessage());
        }
    }
    
    @MessageMapping("games/start")
    @PostMapping("/start")
    public void startGame(
            @RequestBody
            String gameId
    ){
        gameService.startGame(gameId);
    }
    
}
