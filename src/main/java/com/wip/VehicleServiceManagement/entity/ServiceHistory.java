package com.wip.VehicleServiceManagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
/**
 * ServiceHistory.
 *
 * @author Devadarshini M
 * @author Sridevi Srikumar
 */

@Entity
@Table(name = "service_history")
public class ServiceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private LocalDate serviceDate;

    private String serviceStatus;

    private String remarks;

    private Double totalCost;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private ServiceBooking booking;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Admin updatedBy;

    public ServiceHistory() {}

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ServiceBooking getBooking() {
        return booking;
    }

    public void setBooking(ServiceBooking booking) {
        this.booking = booking;
    }

    public Admin getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Admin updatedBy) {
        this.updatedBy = updatedBy;
    }

    @OneToOne(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}