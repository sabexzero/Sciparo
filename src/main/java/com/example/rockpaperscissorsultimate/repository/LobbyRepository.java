package com.example.rockpaperscissorsultimate.repository;

import com.example.rockpaperscissorsultimate.domain.lobby.Lobby;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LobbyRepository extends MongoRepository<Lobby, String> {
}
