package com.example.rockpaperscissorsultimate.repositories;

import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    public Role findByTitle(String title);
}
