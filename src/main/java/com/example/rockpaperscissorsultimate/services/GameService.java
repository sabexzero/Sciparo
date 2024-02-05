package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Game;
import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.repositories.GameRepository;
import com.example.rockpaperscissorsultimate.utils.dtos.RegisterGameResultRequest;
import com.example.rockpaperscissorsultimate.utils.exceptions.Game.FailedToCreateGameException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GameService {
    
    private final GameRepository gameRepository;
    
    private final StatsService statsService;
    private final PlayerService playerService;
    private final LobbyService lobbyService;
    
/*    @Transactional
    public Game conductGame(RegisterGameResultRequest request){
        Player winner = playerService.getPlayerById(request.getWinnerId());
        Player loser = playerService.getPlayerById(request.getLoserId());
        
        statsService.registerWin(winner, request.getBet());
        statsService.registerLose(loser, request.getBet());
        
        Game newGame = Game.builder()
                .winner(winner)
                .loser(loser)
                .winnerChoice(request.getWinnerChoice())
                .loserChoice(request.getLoserChoice())
                .bet(request.getBet())
                .build();
        
        try{
            return gameRepository.save(newGame);
        }catch (Exception exception){
            throw new FailedToCreateGameException("An error occurred when registering a player");
        }
        
    }*/
    
    @Transactional
    public void joinPlayerLobby(String playerId, String lobbyId){
        var player = playerService.getPlayerById(playerId);
        lobbyService.joinPlayerLobby(lobbyId,player);
    }
    
}
