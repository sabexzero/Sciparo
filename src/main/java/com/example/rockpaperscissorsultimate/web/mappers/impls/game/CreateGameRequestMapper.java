package com.example.rockpaperscissorsultimate.web.mappers.impls.game;

import com.example.rockpaperscissorsultimate.domain.game.Game;
import com.example.rockpaperscissorsultimate.utils.GameUtils;
import com.example.rockpaperscissorsultimate.web.dto.game.CreateGameRequest;
import com.example.rockpaperscissorsultimate.web.mappers.interfaces.CreateRequestMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateGameRequestMapper implements CreateRequestMapper<Game, CreateGameRequest> {
    @Override
    public Game toEntity(CreateGameRequest createRequest) {
        return Game.builder()
                .bet(createRequest.bet())
                .firstPlayer(createRequest.creator())
                .secondPlayer(null)
                .gameStatus(GameUtils.INITIAL_GAME_STATUS)
                .gameResult(null)
                .firstPlayerWinRounds(0)
                .secondPlayerWinRounds(0)
                .readyPlayers(0)
                .roundsAmount(0)
                .build();
    }
    
    @Override
    public List<Game> toEntity(List<CreateGameRequest> createRequests) {
        List<Game> result = new ArrayList<>();
        for (var createRequest:createRequests) {
            result.add(Game.builder()
                    .bet(createRequest.bet())
                    .firstPlayer(createRequest.creator())
                    .secondPlayer(null)
                    .gameStatus(GameUtils.INITIAL_GAME_STATUS)
                    .gameResult(null)
                    .firstPlayerWinRounds(0)
                    .secondPlayerWinRounds(0)
                    .readyPlayers(0)
                    .roundsAmount(0)
                    .build());
        }
        return result;
    }
}
