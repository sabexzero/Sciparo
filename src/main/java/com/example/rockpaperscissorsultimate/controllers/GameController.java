package com.example.rockpaperscissorsultimate.controllers;


import com.example.rockpaperscissorsultimate.models.Game;
import com.example.rockpaperscissorsultimate.services.GameService;
import com.example.rockpaperscissorsultimate.utils.dtos.RegisterGameResultRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/games")
public class GameController {
    
    private final GameService gameService;
    
    @PostMapping("/conductGame")
    public ResponseEntity<Game> ConductGame(@RequestBody RegisterGameResultRequest dto){
        return new ResponseEntity<>(gameService.conductGame(dto), HttpStatus.OK);
    }
}
