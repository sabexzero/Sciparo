package com.example.rockpaperscissorsultimate.web.security.jwt;

import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.domain.player.Role;
import com.example.rockpaperscissorsultimate.service.props.JwtProperties;
import com.example.rockpaperscissorsultimate.web.dto.security.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final PlayerService playerService;
    private SecretKey key;
    
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
    
    public String createAccessToken(
            final String userId,
            final String username,
            final Set<Role> roles
    ) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", userId)
                .add("roles", resolveRoles(roles))
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }
    
    public String createAccessTokenForTesting() {
        Claims claims = Jwts.claims()
                .subject("test")
                .add("id", "123456789")
                .add("roles", resolveRoles(Set.of(Role.valueOf("USER"))))
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }
    
    private List<String> resolveRoles(
            final Set<Role> roles
    ) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
    
    public String createRefreshToken(
            final String userId,
            final String username
    ) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }
    
    public JwtResponse refreshUserTokens(
            final String refreshToken
    ) {
        if (!isValid(refreshToken)) {
            //TODO: Message constant
            throw new AccessDeniedException("Access Denied");
        }
        String playerId = String.valueOf(getId(refreshToken));
        Player player = playerService.getPlayerById(playerId);
        return JwtResponse.builder()
                .id(playerId)
                .username(player.getUsername())
                .accessToken(createAccessToken(playerId,player.getUsername(),player.getRoles()))
                .build();
    }
    
    public boolean isValid(
            final String token
    ) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }
    
    private String getId(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }
    
    private String getUsername(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    public Authentication getAuthentication(
            final String token
    ) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                username
        );
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }
}
