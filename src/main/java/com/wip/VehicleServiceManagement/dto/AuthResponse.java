package com.wip.VehicleServiceManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing the authentication response containing the JWT.
 */
@Schema(description = "DTO representing the response returned after a successful authentication")
public class AuthResponse {

    @Schema(description = "The generated JSON Web Token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    public AuthResponse() {}

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
