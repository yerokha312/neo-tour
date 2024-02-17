package com.yerokha.neotour.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Yerbolat",
                        email = "yerbolatt312@gmail.com",
                        url = "https://t.me/yerokhych"
                ),
                title = "NeoTour API",
                description = "OpenApi documentation for NeoTour Spring Project",
                version = "0.0.1"
        ),
        servers = {
                @Server(
                        description = "Railway App",
                        url = "https://neo-tour-production.up.railway.app"
                )
        }
)
public class OpenApiConfig {
}
