package com.example.rockpaperscissorsultimate.web.security.jwt;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    
    private final PlayerService playerService;
    
    @Override
    public UserDetails loadUserByUsername(
            final String username
    ) {
        Player player = playerService.getPlayerByName(username);
        return JwtEntityFactory.create(player);
    }
    
}
