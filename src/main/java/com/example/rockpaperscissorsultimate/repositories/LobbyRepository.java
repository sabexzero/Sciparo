package com.example.rockpaperscissorsultimate.repositories;

import com.example.rockpaperscissorsultimate.models.Game;
import com.example.rockpaperscissorsultimate.models.Lobby;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface LobbyRepository extends MongoRepository<Lobby, String> {
}
