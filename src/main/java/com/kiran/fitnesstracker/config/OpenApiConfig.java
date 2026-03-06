package com.kiran.fitnesstracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/*
    OpenAPI/Swagger Configuration for API documentation
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
            title = "Fitness Tracker & Coaching System API",
            version = "1.0.0",
            description = "REST API for fitness tracking and workout coaching platform",
            contact = @Contact(
                    name = "Fitness Tracker Team",
                    email = "Support@fitnesstracker.com"
            ),
            license = @License(
                    name = "Apache 2.0",
                    url = "https://www.apache.org/licenses/LICENSE-2.0.html"
            )
    ),
        servers = {
            @Server(
                    url = "http://localhost:8080/",
                    description = "Development server"
            ),
                @Server(
                        url = "https://api.fitnesstracker.com/",
                        description = "Production server"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Enter JWT token"
)
public class OpenApiConfig {
}
