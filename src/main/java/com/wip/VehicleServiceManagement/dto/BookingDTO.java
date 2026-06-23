package com.wip.VehicleServiceManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
/**
 * BookingDTO.
 *
 * @author Devadarshini M
 */

public class BookingDTO {

    private Long bookingId;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    private Long serviceId;

    private java.util.List<Long> serviceIds;

    @NotBlank(message = "Problem description is required")
    private String problemDescription;

    private String status;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public java.util.List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(java.util.List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}