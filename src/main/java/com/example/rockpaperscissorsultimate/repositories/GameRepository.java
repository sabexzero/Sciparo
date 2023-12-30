package com.example.rockpaperscissorsultimate.repositories;

import com.example.rockpaperscissorsultimate.models.Game;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface GameRepository extends JpaRepository<Game, UUID> {
}
