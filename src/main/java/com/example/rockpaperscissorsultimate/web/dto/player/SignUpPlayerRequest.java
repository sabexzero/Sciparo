package com.example.rockpaperscissorsultimate.web.dto.player;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;


public record SignUpPlayerRequest (
    @Schema(
            description = "The user's alias",
            example = "Warrior78"
    )
    @Size(
            min = 4,
            max = 15,
            message = "Username length must be between 4 and 15 characters"
    )
    @NotBlank(
            message = "Username cannot be empty"
    )
    String username,
    
    @Schema(
            description = "Email address",
            example = "warrior78@gmail.com"
    )
    @Email(
            message = "Email should be valid"
    )
    @NotBlank(
            message = "Email cannot be empty"
    )
    String email,
    
    @Schema(
            description = "A secret sequence confirming account ownership",
            example = "my_1secret1_Password!"
    )
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
            message = "The password must contain at least one digit, " +
                    "one lowercase and one uppercase letter and be at least 6 characters long"
    )
    @NotBlank(
            message = "Password cannot be empty"
    )
    String passwordText
) {}
