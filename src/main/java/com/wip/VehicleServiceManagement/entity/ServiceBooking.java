package com.wip.VehicleServiceManagement.entity;

import jakarta.persistence.*;
/**
 * ServiceBooking.
 *
 * @author Devadarshini M
 * @author Sridevi Srikumar
 */

@Entity
@Table(name = "service_booking")
public class ServiceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "booking_services",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private java.util.List<ServiceEntity> services = new java.util.ArrayList<>();

    private String status;

    private String problemDescription;

    public ServiceBooking() {}

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public java.util.List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(java.util.List<ServiceEntity> services) {
        this.services = services;
    }

    public ServiceEntity getService() {
        if (services != null && !services.isEmpty()) {
            return services.get(0);
        }
        return null;
    }

    public void setService(ServiceEntity service) {
        if (this.services == null) {
            this.services = new java.util.ArrayList<>();
        } else {
            this.services.clear();
        }
        if (service != null) {
            this.services.add(service);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private ServiceHistory serviceHistory;

    public ServiceHistory getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistory = serviceHistory;
    }
}