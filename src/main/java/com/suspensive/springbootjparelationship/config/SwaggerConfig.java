package com.suspensive.springbootjparelationship.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Notes-App",
                description = "This is a simple API where you can manage your notes, and select them by tags",
                version = "1.0.0",
                contact = @Contact(
                        name = "Jeferson Ospina",
                        email = "jeospínaga@unal.edu.co"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"

                )
        }
)
public class SwaggerConfig {
}
