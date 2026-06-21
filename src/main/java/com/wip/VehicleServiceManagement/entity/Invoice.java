package com.wip.VehicleServiceManagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    private Double amount;

    private String paymentStatus;

    private LocalDate invoiceDate;

    @OneToOne
    @JoinColumn(name = "history_id")
    private ServiceHistory history;

    @ManyToOne
    @JoinColumn(name = "generated_by")
    private Admin generatedBy;

    public Invoice() {}

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public ServiceHistory getHistory() {
        return history;
    }

    public void setHistory(ServiceHistory history) {
        this.history = history;
    }

    public Admin getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(Admin generatedBy) {
        this.generatedBy = generatedBy;
    }
}