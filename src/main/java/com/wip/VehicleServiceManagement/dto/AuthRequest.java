package com.wip.VehicleServiceManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a login request.
 */
@Schema(description = "DTO representing the user login credentials")
public class AuthRequest {

    @Schema(description = "Username of the administrator", example = "admin", required = true)
    private String username;

    @Schema(description = "Password of the administrator", example = "admin123", required = true)
    private String password;

    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
