package com.example.rockpaperscissorsultimate.controllers;

import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.services.PlayerService;
import com.example.rockpaperscissorsultimate.services.RoleService;
import com.example.rockpaperscissorsultimate.utils.dtos.CreatePlayerRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/players")
public class PlayersController {
    private final PlayerService playerService;
    private final RoleService roleService;
    
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerRequest dto) {
        Player newPLayer = playerService.createPlayer(dto);
        roleService.setPlayerRole(newPLayer,roleService.getRoleByName("USER"));
        
        return new ResponseEntity<>(newPLayer, HttpStatus.OK);
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<Player>> readAllPlayers(){
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteById/{id}")
    public HttpStatus deletePlayerById(@PathVariable UUID id){
        playerService.deletePlayerById(id);
        return HttpStatus.OK;
    }
}