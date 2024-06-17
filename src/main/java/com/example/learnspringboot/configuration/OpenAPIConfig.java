package com.example.learnspringboot.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Profile("!prod")
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI(@Value("${open.api.title}") String title, @Value("${open.api.version}") String version, @Value("${open.api.description}") String description, @Value("${open.api.serverUrl}") String serverUrl, @Value("${open.api.serverName}") String serverName) {
        return new OpenAPI().info(new Info().title(title).version(version).description(description)
                        .license(new License().name("API License").url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(new Server().url(serverUrl).description(serverName)));
//                .components(
//                        new Components()
//                                .addSecuritySchemes(
//                                        "bearerAuth",
//                                        new SecurityScheme()
//                                                .type(SecurityScheme.Type.HTTP)
//                                                .scheme("bearer")
//                                                .bearerFormat("JWT")
//                                )
//                )
//                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("api-service")
                .packagesToScan("com.example.learnspringboot.controller")
                .build();
    }
}
