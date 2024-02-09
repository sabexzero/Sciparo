package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Game;
import com.example.rockpaperscissorsultimate.models.Lobby;
import com.example.rockpaperscissorsultimate.repositories.GameRepository;
import com.example.rockpaperscissorsultimate.utils.dtos.MoveResponse;
import com.example.rockpaperscissorsultimate.utils.dtos.RegisterMoveRequest;
import com.example.rockpaperscissorsultimate.utils.enums.GameResult;
import com.example.rockpaperscissorsultimate.utils.exceptions.Game.FailedToCreateGameException;
import com.example.rockpaperscissorsultimate.utils.exceptions.Game.GameNotFoundException;
import com.example.rockpaperscissorsultimate.utils.game.GameUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class GameService {
    
    private final GameRepository gameRepository;
    
    private final StatsService statsService;
    private final PlayerService playerService;
    private final LobbyService lobbyService;
    
    @Transactional
    @SneakyThrows(value = FailedToCreateGameException.class)
    public void conductGame(Game endedGame){
        
        int deltaWins = endedGame.getFirstPlayerWinRounds() - endedGame.getSecondPlayerWinRounds();
        
        if(deltaWins != 0){
            if(deltaWins > 0){
                endedGame.setGameResult(GameResult.PLAYER1_WON);
                statsService.changeStatsAfterGame(endedGame.getFirstPlayer(),endedGame.getSecondPlayer(),endedGame.getBet());
            }
            else{
                endedGame.setGameResult(GameResult.PLAYER2_WON);
                statsService.changeStatsAfterGame(endedGame.getSecondPlayer(),endedGame.getFirstPlayer(),endedGame.getBet());
            }
        }
    }
    
    public void createGame(Lobby lobby){
        
        var player1 = playerService.getPlayerById(lobby.getPlayersId().get(0));
        
        var player2 = playerService.getPlayerById(lobby.getPlayersId().get(lobby.getPlayersId().size() - 1));
        
        Game game = Game.builder()
                .firstPlayer(player1)
                .secondPlayer(player2)
                .firstPlayerWinRounds(0)
                .roundsAmount(0)
                .secondPlayerWinRounds(0)
                .bet(lobby.getBet())
                .lobbyId(lobby.getId())
                .build();
        
        gameRepository.save(game);
    }
    
    /**
     * @return
     * If the game continues, true. otherwise false
     */
    public MoveResponse registerMove(RegisterMoveRequest request){
        
        var game = gameRepository.findById(request.getLobbyId()).orElseThrow(GameNotFoundException::new);
        
        GameResult roundResult = GameUtils.determineRoundResult(request.getFirstPlayerChoice(),request.getSecondPlayerChoice());
        
        if (roundResult == GameResult.PLAYER1_WON)
            game.setFirstPlayerWinRounds(game.getFirstPlayerWinRounds() + 1);
        else
            game.setSecondPlayerWinRounds(game.getSecondPlayerWinRounds() + 1);
        
        //We check the number of remaining cards (two players have the same number),
        // if the number of remaining pairs of cards is not enough to change the result of the game,
        // it should end
        if((GameUtils.CARDS_AMOUNT -  game.getRoundsAmount()) > Math.abs(game.getFirstPlayerWinRounds() - game.getSecondPlayerWinRounds())){
            game.setRoundsAmount(game.getRoundsAmount()+1);
            gameRepository.save(game);
            return new MoveResponse(roundResult, false,game);
        }
        else{
            return new MoveResponse(roundResult, true,game);
        }
        
    }
    
}
