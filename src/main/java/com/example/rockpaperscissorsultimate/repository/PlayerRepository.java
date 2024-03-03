package com.example.rockpaperscissorsultimate.repository;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByUsername(String username);
}