package com.example.rockpaperscissorsultimate.web.controllers;

import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.service.GameService;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.web.dto.game.CreateGameRequest;
import com.example.rockpaperscissorsultimate.web.dto.game.MoveResponse;
import com.example.rockpaperscissorsultimate.web.dto.game.PlayerInOutRequest;
import com.example.rockpaperscissorsultimate.web.dto.game.RegisterMoveRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
public class GameControllerRest {
    
    private final GameService gameService;
    private final PlayerService playerService;
    @PostMapping("/new")
    @Operation(
            summary = "Creating game",
            description = "Allows you to create a game based on the existing lobby, " +
                    "if all the players are gathered and ready to start the game"
    )

    public ResponseEntity<?> createGame(
            @RequestBody
            @Parameter(description = "The essence of the lobby on the basis of which the game is created")
            CreateGameRequest request
    ){
        try {
            Player creator = playerService.getPlayerById(request.creatorId());
            Game createdGame = gameService.createGame(creator,request.bet());
            return new ResponseEntity<>(createdGame, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/ready")
    public ResponseEntity<?> readyPlayer(
            @RequestBody
            String gameId
    ){
        try {
            return new ResponseEntity<>(gameService.addReadyStatus(gameId), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping ("/ready")
    public ResponseEntity<?> unreadyPlayer(
            @RequestBody
            String gameId
    ){
        try {
            return new ResponseEntity<>(gameService.removeReadyStatus(gameId), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
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
    
    @PostMapping("/leave")
    public void leavePlayer(
            @RequestBody
            PlayerInOutRequest request
    ){
        try {
            Player player = playerService.getPlayerById(request.playerId());
            gameService.leavePlayer(request.gameId(),player);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    
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
        MoveResponse moveResponse = gameService.registerMove(request);
        
        if(moveResponse.isLastMove()){
            gameService.conductGame(moveResponse.game());
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
