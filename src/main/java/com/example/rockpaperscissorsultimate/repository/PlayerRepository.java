package com.example.rockpaperscissorsultimate.repository;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;

@Lazy
public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByUsername(String username);
}