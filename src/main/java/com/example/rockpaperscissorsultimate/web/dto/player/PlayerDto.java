package com.example.rockpaperscissorsultimate.web.dto.player;

import com.example.rockpaperscissorsultimate.web.dto.validation.OnCreate;
import com.example.rockpaperscissorsultimate.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


/**
 * DTO for transmitting user data, not including authorization-related data
 */

@Schema(description = "Player DTO")
public record PlayerDto (
    @Schema(
            description = "Player id",
            example = "78c633dbc08y6a5f1td081f2"
    )
    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    String id,
    
    @Schema(
            description = "User nickname",
            example = "johndoe146"
    )
    @NotNull(
            message = "Username must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Length(
            max = 15,
            message = "Username length must be smaller than 15 symbols.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    String username,
    
    Integer wins,
    Integer loses,
    Integer draws,
    Integer elo,
    Long coins
) {}
