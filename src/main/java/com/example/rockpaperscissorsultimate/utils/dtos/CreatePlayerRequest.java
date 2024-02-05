package com.example.rockpaperscissorsultimate.utils.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePlayerRequest {
    
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters")
    @NotNull(message = "Username cannot be empty")
    private String username;
    
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be empty")
    private String email;
    
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
            message = "The password must contain at least one digit, one lowercase and one uppercase letter and be at least 6 characters long")
    private String passwordText;
}
