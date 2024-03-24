package com.example.rockpaperscissorsultimate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

//Config
@OpenAPIDefinition(
        info = @Info(
                title = "Sciparo Api",
                description = "\"Sciparo API\" - Developer interface for seamless integration and access to Sciparo game functionalities and data. Elevate gaming experiences by incorporating Sciparo's card battles, " +
                        "player information, and other features into external applications.", version = "1.0.0",
                contact = @Contact(
                        name = "Kuksa Vitaliy",
                        email = "vkuksa.tech@outlook.com",
                        url = "https://vk.com/blidov"
                )
        )
)
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Task list API")
                        .description("Demo Spring Boot application")
                        .version("1.0")
                );
    }
}
