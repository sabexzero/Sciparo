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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for creating games and
 * performing all the actions inside each game
 */


@AllArgsConstructor
@Tag(
        name="GameControllerWebSockets",
        description="This controller is responsible for creating games and" +
                " performing all the actions inside each game by websockets"
)
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
        try {
            Player creator = playerService.getPlayerById(request.creatorId());
            gameService.createGame(creator,request.bet());
            
            messagingTemplate.convertAndSend(
                    GameUtils.GAME_TOPIC,
                    GameUtils.getGameCreatedString(creator.getUsername(),request.bet())
            );
        }catch (Exception ex){
            messagingTemplate.convertAndSend(
                    GameUtils.GAME_TOPIC,
                    new FailedToCreateGameException().getMessage()
            );
        }
    }
    
    @MessageMapping("games/start")
    public void startGame(
            @RequestBody
            String gameId
    ){
        String matchTopic = GameUtils.GAME_TOPIC + gameId;
        try{
            gameService.startGame(gameId);
            messagingTemplate.convertAndSend(
                    matchTopic,
                    GameUtils.START_MESSAGE
            );
        } catch(Exception ex) {
            messagingTemplate.convertAndSend(
                    matchTopic,
                    ex.getMessage()
            );
        }
    }
    
    @MessageMapping("games/move")
    @Operation(
            summary = "Registers the player's move",
            description = "Registers the player's move during the game"
    )
    public void registerMove(
            @RequestBody
            @Parameter(description = "The essence of the request for registration of the move")
            RegisterMoveRequest request
    ){
        String matchTopic = GameUtils.GAME_TOPIC + request.gameId();
        
        try {
            MoveResponse moveResponse = gameService.registerMove(request);
            
            messagingTemplate.convertAndSend(
                    matchTopic,
                    GameUtils.getRoundResultString(
                            moveResponse.game().getRoundsAmount(),
                            moveResponse.moveResult().toString()
                    )
            );
            
            if(moveResponse.isLastMove()){
                Game endedGame = gameService.conductGame(moveResponse.game());
                
                messagingTemplate.convertAndSend(
                        matchTopic,
                        GameUtils.getGameResultString(
                                endedGame.getGameResult().toString()
                        )
                );
            }
            
        }catch (Exception ex){
            messagingTemplate.convertAndSend(
                    matchTopic,
                    ex.getMessage()
            );
        }
    }
    
    @MessageMapping("games/ready")
    public void readyPlayer(
            @RequestBody
            String gameId
    ){
        String matchTopic = GameUtils.GAME_TOPIC + gameId;
        try {
            Game game = gameService.addReadyStatus(gameId);
            messagingTemplate.convertAndSend(
                    matchTopic,
                    GameUtils.getMatchReadyStatusString(game.getReadyPlayers())
            );
            
        } catch (Exception ex){
            messagingTemplate.convertAndSend(
                    matchTopic,
                    ex.getMessage()
            );
        }
    }
    
    @MessageMapping("games/unready")
    public void unreadyPlayer(
            @RequestBody
            String gameId
    ){
        String matchTopic = GameUtils.GAME_TOPIC + gameId;
        try {
            Game game = gameService.removeReadyStatus(gameId);
            messagingTemplate.convertAndSend(
                    matchTopic,
                    GameUtils.getMatchReadyStatusString(game.getReadyPlayers())
            );
            
        } catch (Exception ex){
            messagingTemplate.convertAndSend(
                    matchTopic,
                    ex.getMessage()
            );
        }
    }
    
    @MessageMapping("games/join")
    public void joinPlayer(
            @RequestBody
            PlayerInOutRequest request
    ){
        String matchTopic = GameUtils.GAME_TOPIC + request.gameId();
        try {
            Player player = playerService.getPlayerById(request.playerId());
            gameService.joinPlayer(request.gameId(),player);
            messagingTemplate.convertAndSend(
                    matchTopic,
                    GameUtils.getPlayerJoinString(player.getUsername())
            );
            
        } catch (Exception ex){
            messagingTemplate.convertAndSend(
                    matchTopic,
                    ex.getMessage()
            );
        }
    }
    
    @MessageMapping("games/leave")
    public void leavePlayer(
            @RequestBody
            PlayerInOutRequest request
    ){
        String matchTopic = GameUtils.GAME_TOPIC + request.gameId();
        try {
            Player player = playerService.getPlayerById(request.playerId());
            gameService.leavePlayer(request.gameId(),player);
            messagingTemplate.convertAndSend(
                    matchTopic,
                    GameUtils.getPlayerLeaveString(player.getUsername())
            );
            
        } catch (Exception ex){
            messagingTemplate.convertAndSend(
                    matchTopic,
                    ex.getMessage()
            );
        }
    }
    
}
