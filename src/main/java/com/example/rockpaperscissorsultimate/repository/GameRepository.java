package com.example.rockpaperscissorsultimate.repository;

import com.example.rockpaperscissorsultimate.domain.game.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
