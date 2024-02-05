package com.example.rockpaperscissorsultimate.controllers;

import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.services.PlayerService;
import com.example.rockpaperscissorsultimate.utils.dtos.CreatePlayerRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/players")
public class PlayersController {
    private final PlayerService playerService;
    
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerRequest dto) {
        Player newPLayer = playerService.createPlayer(dto);
        return new ResponseEntity<>(newPLayer, HttpStatus.OK);
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> readAllPlayers(){
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteById/{id}")
    public HttpStatus deletePlayerById(@PathVariable String id){
        playerService.deletePlayerById(id);
        return HttpStatus.OK;
    }
}