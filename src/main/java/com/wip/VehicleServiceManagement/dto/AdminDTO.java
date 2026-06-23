package com.wip.VehicleServiceManagement.dto;
/**
 * AdminDTO.
 *
 * @author Devadarshini M
 */

public class AdminDTO {

    private Long adminId;
    private String username;
    private String email;
    private String password;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}