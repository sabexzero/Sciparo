package com.example.rockpaperscissorsultimate.web.controllers;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.web.dto.player.ChangePlayerStatusRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller is responsible for working with players, creating them, deleting them, etc.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class PlayersController {
    private final PlayerService playerService;
    
    @MessageMapping("/status")
    public HttpStatus changePlayerStatus(
            @RequestBody ChangePlayerStatusRequest request
    ){
        try{
            playerService.changePlayerStatus(request.getPlayerId(),request.getStatus());
            return HttpStatus.OK;
            
        }catch (Exception ex){
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<Player>> readAllPlayers(){
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public HttpStatus deletePlayerById(
            @PathVariable String id
    ){
        playerService.deletePlayerById(id);
        return HttpStatus.OK;
    }
}