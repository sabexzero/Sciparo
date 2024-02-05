package com.example.rockpaperscissorsultimate.repositories;

import com.example.rockpaperscissorsultimate.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PlayerRepository extends MongoRepository<Player, String> {
    public Player findByUsername(String username);
}