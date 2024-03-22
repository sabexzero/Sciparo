package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.exceptions.game.GameNotFoundByIdException;
import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.repository.GameRepository;
import com.example.rockpaperscissorsultimate.web.dto.game.MoveResponse;
import com.example.rockpaperscissorsultimate.web.dto.game.RegisterMoveRequest;
import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GameService {
    
    private final GameRepository gameRepository;
    
    private final StatsService statsService;
    private final PlayerService playerService;
    
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
    
    public Game createGame(Game game){
        return gameRepository.save(game);
    }
    
    /**
     * If the game continues, true. otherwise false
     */
    public MoveResponse registerMove(RegisterMoveRequest request){
        
        var game = gameRepository.findById(request.gameId()).orElseThrow(() -> new GameNotFoundByIdException(request.gameId()));
        
        GameResult roundResult = GameUtils.determineRoundResult(request.firstPlayerChoice(),request.secondPlayerChoice());
        
        if (roundResult == GameResult.PLAYER1_WON)
            game.setFirstPlayerWinRounds(game.getFirstPlayerWinRounds() + 1);
        if (roundResult == GameResult.PLAYER2_WON)
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
