package com.example.rockpaperscissorsultimate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/*@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    
    @Value("${frontend.url}")
    private String frontendUrl;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(settings -> settings.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(auth -> auth.successHandler(oAuth2LoginSuccessHandler))
                .build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(frontendUrl));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        
        return urlBasedCorsConfigurationSource;
    }
}*/
