package com.example.tictactoeultimate.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePlayerRequest {
    private String username;
    private String email;
    private String password;
    private String name;
}
