package com.example.rockpaperscissorsultimate.web.mappers.impls.player;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.web.dto.player.PlayerDto;
import com.example.rockpaperscissorsultimate.web.mappers.interfaces.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerMapper implements DtoMapper<Player, PlayerDto> {
    @Override
    public PlayerDto toDto(
            Player entity
    ) {
        return new PlayerDto(
                entity.getId(),
                entity.getUsername(),
                entity.getWins(),
                entity.getLoses(),
                entity.getDraws(),
                entity.getElo(),
                entity.getCoins()
                );
    }
    
    @Override
    public List<PlayerDto> toDto(List<Player> entities) {
        List<PlayerDto> result = new ArrayList<>();
        for (var entity:entities) {
            result.add(new PlayerDto(
                    entity.getId(),
                    entity.getUsername(),
                    entity.getWins(),
                    entity.getLoses(),
                    entity.getDraws(),
                    entity.getElo(),
                    entity.getCoins()
            ));
        }
        return result;
    }
}
