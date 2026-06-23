package com.wip.VehicleServiceManagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 * ServiceDTO.
 *
 * @author Devadarshini M
 */

public class ServiceDTO {

    private Long serviceId;

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotBlank(message = "Service description is required")
    private String description;

    @NotNull(message = "Base cost is required")
    @DecimalMin(value = "0.0", message = "Base cost must be 0.0 or greater")
    private Double baseCost;

    private Long adminId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(Double baseCost) {
        this.baseCost = baseCost;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}