package com.example.rockpaperscissorsultimate.repository;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Lazy
public interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<Player> findByUsername(String username);
}