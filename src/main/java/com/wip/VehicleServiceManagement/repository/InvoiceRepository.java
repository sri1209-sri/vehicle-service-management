package com.wip.VehicleServiceManagement.repository;

import com.wip.VehicleServiceManagement.entity.Invoice;
import com.wip.VehicleServiceManagement.entity.ServiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * InvoiceRepository.
 *
 * @author Sridevi Srikumar
 */

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Invoice findByHistory(ServiceHistory history);

    Invoice findByPaymentStatus(String status);
}