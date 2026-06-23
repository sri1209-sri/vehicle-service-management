package com.wip.VehicleServiceManagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * OpenApiConfig.
 *
 * @author Devadarshini M
 */

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Vehicle Service Management System (Vehi-Serve) API")
                        .version("1.0.0")
                        .description("REST API specifications for the Vehi-Serve Capstone Project.\n\n" +
                                     "**Authorization**: Secured endpoints (under `/api/**`) require a JWT token. " +
                                     "First, authenticate by calling the `/api/auth/login` endpoint with valid credentials. " +
                                     "Then, copy the `token` from the response, click the green **'Authorize'** button at the top-right, " +
                                     "paste the token, and click Authorize."))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuthentication"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuthentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}
