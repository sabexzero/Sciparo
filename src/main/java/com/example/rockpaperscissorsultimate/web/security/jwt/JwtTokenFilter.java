package com.example.rockpaperscissorsultimate.web.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    
    private final JwtTokenProvider jwtTokenProvider;
    
    @Override
    @SneakyThrows
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String bearerToken = httpServletRequest.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        
        try {
            if (bearerToken != null && jwtTokenProvider.isValid(bearerToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("User '{}' successfully authenticated using JWT", authentication.getName());
                }
            }
        } catch (Exception e) {
            logger.error("Error during JWT processing: {}", e.getMessage());
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
}
