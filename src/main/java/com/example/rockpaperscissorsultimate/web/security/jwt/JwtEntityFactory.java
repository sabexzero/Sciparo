package com.example.rockpaperscissorsultimate.web.security.jwt;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.domain.player.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtEntityFactory {
    
    public static JwtEntity create(
            final Player player
    ) {
        return new JwtEntity(
                player.getId(),
                player.getUsername(),
                player.getPasswordHash(),
                mapToGrantedAuthorities(new ArrayList<>(player.getRoles()))
        );
    }
    
    private static List<GrantedAuthority> mapToGrantedAuthorities(
            final List<Role> roles
    ) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    
}
