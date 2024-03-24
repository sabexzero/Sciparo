package com.example.rockpaperscissorsultimate.service;

import com.example.rockpaperscissorsultimate.domain.exceptions.game.GameNotFoundByIdException;
import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.domain.game.GameStatus;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.repository.GameRepository;
import com.example.rockpaperscissorsultimate.web.dto.game.MoveResponse;
import com.example.rockpaperscissorsultimate.web.dto.game.RegisterMoveRequest;
import com.example.rockpaperscissorsultimate.domain.game.GameResult;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

import javax.naming.OperationNotSupportedException;
import java.security.InvalidParameterException;

@AllArgsConstructor
@Service
public class GameService {
    
    private final GameRepository gameRepository;
    
    private final StatsService statsService;
    
    public void conductGame(
            Game endedGame
    ){
        
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
    
    public Game createGame(
            Player creator,
            Integer bet
    ){
        Game createdGame = Game.builder()
                .bet(bet)
                .firstPlayer(creator)
                .secondPlayer(null)
                .gameStatus(GameUtils.INITIAL_GAME_STATUS)
                .gameResult(null)
                .firstPlayerWinRounds(0)
                .secondPlayerWinRounds(0)
                .readyPlayers(0)
                .roundsAmount(0)
                .build();
        return gameRepository.save(createdGame);
    }
    
    public void joinPlayer(
            String gameId,
            Player player
    ) throws GameNotFoundByIdException{
        Game gameToJoin = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundByIdException(gameId));
        gameToJoin.setSecondPlayer(player);
        gameRepository.save(gameToJoin);
    }
    
    public void removePlayer(
            String gameId,
            Player player
    ) throws RuntimeException{
        Game gameToJoin = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundByIdException(gameId));
        if(gameToJoin.getFirstPlayer().equals(player)){
            gameToJoin.setFirstPlayer(null);
        } else if (gameToJoin.getSecondPlayer().equals(player)) {
            gameToJoin.setSecondPlayer(null);
        } else{
            //TODO: Constant message
            throw new IllegalArgumentException("Player not found in this game");
        }
        gameRepository.save(gameToJoin);
    }
    
    public Game addReadyStatus(
            String gameId
    ){
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundByIdException(gameId));
        if(game.getReadyPlayers() != GameUtils.MAX_PLAYERS){
            game.setReadyPlayers(game.getReadyPlayers()+1);
            return gameRepository.save(game);
        } else{
            throw new UnsupportedOperationException("All players are ready");
        }
    }
    
    public void startGame(String gameId){
        Game gameToStart = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundByIdException(gameId));
        gameToStart.setGameStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(gameToStart);
    }
    
    public void removeReadyStatus(
            String gameId
    ){
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundByIdException(gameId));
        if(game.getReadyPlayers() != 0){
            game.setReadyPlayers(game.getReadyPlayers()-1);
            gameRepository.save(game);
        } else{
            throw new UnsupportedOperationException("No one of players are ready");
        }
    }
    
    /**
     * If the game continues, true. otherwise false
     */
    public MoveResponse registerMove(
            RegisterMoveRequest request
    ){
        
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
