package com.wip.VehicleServiceManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a Customer.
 */
@Schema(description = "Customer data details")
public class CustomerDTO {

    @Schema(description = "Unique identifier of the customer", example = "1")
    private Long customerId;

    @Schema(description = "Full name of the customer", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the customer", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number of the customer", example = "+91 9876543210")
    private String phone;

    @Schema(description = "Home address of the customer", example = "456 Elm Street, Springfield")
    private String address;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}