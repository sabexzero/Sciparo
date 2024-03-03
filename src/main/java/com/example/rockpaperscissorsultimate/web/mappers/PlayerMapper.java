package com.example.rockpaperscissorsultimate.web.mappers;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.web.dto.player.PlayerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper extends Mappable<Player, PlayerDto>{
}
